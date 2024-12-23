package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Question;
import com.francesco_giuliante.infobasic.model.Quiz;
import com.francesco_giuliante.infobasic.service.QuestionService;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.*;


public class QuizDAO implements GenericDAO<Quiz> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    private QuestionService questionService = new QuestionService();

    @Override
    public Quiz save(Quiz quiz) {
        String sql = "INSERT INTO quizzes (rule_id, professor_id, attempts, average_rating, question_ids_value_x2, question_ids_value_x3) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quiz.getRuleID());
            ps.setInt(2, quiz.getProfessorID());
            ps.setInt(3, quiz.getAttempts());
            ps.setDouble(4, quiz.getAverageRating());

            List<Integer> questionsValueX2 = (quiz.getQuestionIDsValueX2() != null) ? quiz.getQuestionIDsValueX2() : new ArrayList<>();
            List<Integer> questionsValueX3 = (quiz.getQuestionIDsValueX3() != null) ? quiz.getQuestionIDsValueX3() : new ArrayList<>();

            Array valueX2Array = connection.createArrayOf("INTEGER", questionsValueX2.toArray());
            Array valueX3Array = connection.createArrayOf("INTEGER", questionsValueX3.toArray());

            ps.setArray(5, valueX2Array);
            ps.setArray(6, valueX3Array);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                quiz.setId(rs.getInt("id"));
            }
            return quiz;

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving quiz", e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteQuizByIdSQL = "DELETE FROM public.\"quizzes\" WHERE id = ?";

        try (PreparedStatement psDeleteQuiz = connection.prepareStatement(deleteQuizByIdSQL)) {
            psDeleteQuiz.setInt(1, id);
            psDeleteQuiz.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting quiz with ID: " + id, e);
        }
    }

    @Override
    public Quiz update(Quiz quiz, int id) {
        StringBuilder updateQuizSQL = new StringBuilder("UPDATE public.\"quizzes\" SET ");
        List<Object> parameters = new ArrayList<>();

        Map<String, Object> fieldsToUpdate = new HashMap<>();

        if (quiz.getRuleID() != null) fieldsToUpdate.put("rule_id", quiz.getRuleID());
        if (quiz.getSubjectID() != null) fieldsToUpdate.put("subject_id", quiz.getSubjectID());
        if (quiz.getProfessorID() != null) fieldsToUpdate.put("professor_id", quiz.getProfessorID());
        if (quiz.getAttempts() != null) fieldsToUpdate.put("attempts", quiz.getAttempts());
        if (quiz.getAverageRating() != null) fieldsToUpdate.put("average_rating", quiz.getAverageRating());
        if (quiz.getQuestionIDsValueX2() != null && !quiz.getQuestionIDsValueX2().isEmpty()) {
            Integer[] x2Array = quiz.getQuestionIDsValueX2().toArray(new Integer[0]);
            try {
                fieldsToUpdate.put("question_ids_value_x2", connection.createArrayOf("INTEGER", x2Array));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (quiz.getQuestionIDsValueX3() != null && !quiz.getQuestionIDsValueX3().isEmpty()) {
            Integer[] x3Array = quiz.getQuestionIDsValueX3().toArray(new Integer[0]);
            try {
                fieldsToUpdate.put("question_ids_value_x3", connection.createArrayOf("INTEGER", x3Array));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            updateQuizSQL.append(entry.getKey()).append(" = ?, ");
            parameters.add(entry.getValue());
        }

        updateQuizSQL.delete(updateQuizSQL.length() - 2, updateQuizSQL.length());
        updateQuizSQL.append(" WHERE id = ?");

        parameters.add(id);

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement psUpdateQuiz = connection.prepareStatement(updateQuizSQL.toString())) {
                for (int i = 0; i < parameters.size(); i++) {
                    psUpdateQuiz.setObject(i + 1, parameters.get(i));
                }

                int rowsAffected = psUpdateQuiz.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();

                    Quiz quizUpdated = findById(id).orElseThrow(() -> new RuntimeException("No quiz found with ID: " + id));
                    return quizUpdated;
                } else {
                    throw new RuntimeException("No quiz found with ID: " + id);
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Error while updating quiz with ID: " + id, e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while starting transaction for quiz update", e);
        }
    }




    @Override
    public Optional<Quiz> findById(int id) {
        String findQuizByIdSQL = "SELECT * FROM public.\"quizzes\" WHERE id = ?";

        try (PreparedStatement psFindQuiz = connection.prepareStatement(findQuizByIdSQL)) {
            psFindQuiz.setInt(1, id);

            try (ResultSet rs = psFindQuiz.executeQuery()) {
                if (rs.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setId(rs.getInt("id"));
                    quiz.setRuleID(rs.getInt("rule_id"));
                    quiz.setSubjectID(rs.getInt("subject_id"));
                    quiz.setProfessorID(rs.getInt("professor_id"));
                    quiz.setAttempts(rs.getInt("attempts"));
                    quiz.setAverageRating(rs.getDouble("average_rating"));

                    Array x2Array = rs.getArray("question_ids_value_x2");
                    if (x2Array != null) {
                        Integer[] x2Values = (Integer[]) x2Array.getArray();
                        quiz.setQuestionIDsValueX2(new ArrayList<>(List.of(x2Values)));
                    }

                    Array x3Array = rs.getArray("question_ids_value_x3");
                    if (x3Array != null) {
                        Integer[] x3Values = (Integer[]) x3Array.getArray();
                        quiz.setQuestionIDsValueX3(new ArrayList<>(List.of(x3Values)));
                    }

                    String getQuestionsForQuizSQL = "SELECT question_id FROM public.\"quiz_questions\" WHERE quiz_id = ?";
                    try (PreparedStatement psQuestions = connection.prepareStatement(getQuestionsForQuizSQL)) {
                        psQuestions.setInt(1, quiz.getId());
                        try (ResultSet questionRS = psQuestions.executeQuery()) {
                            List<Question> questions = new ArrayList<>();
                            while (questionRS.next()) {
                                int questionId = questionRS.getInt("question_id");
                                Optional<Question> questionOpt = questionService.getQuestionById(questionId);
                                questionOpt.ifPresent(questions::add);
                            }
                            quiz.setQuestions(questions);
                        }
                    }

                    return Optional.of(quiz);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding quiz with ID: " + id, e);
        }
        return Optional.empty();
    }


    @Override
    public List<Quiz> findAll() {
        String findAllQuizSQL = "SELECT * FROM public.\"quizzes\"";
        List<Quiz> quizList = new ArrayList<>();

        try (PreparedStatement psFindAllQuiz = connection.prepareStatement(findAllQuizSQL);
             ResultSet rs = psFindAllQuiz.executeQuery()) {
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt("id"));
                quiz.setRuleID(rs.getInt("rule_id"));
                quiz.setSubjectID(rs.getInt("subject_id"));
                quiz.setProfessorID(rs.getInt("professor_id"));
                quiz.setAttempts(rs.getInt("attempts"));
                quiz.setAverageRating(rs.getDouble("average_rating"));

                Array x2Array = rs.getArray("question_ids_value_x2");
                if (x2Array != null) {
                    Integer[] x2Values = (Integer[]) x2Array.getArray();
                    quiz.setQuestionIDsValueX2(new ArrayList<>(List.of(x2Values)));
                }

                Array x3Array = rs.getArray("question_ids_value_x3");
                if (x3Array != null) {
                    Integer[] x3Values = (Integer[]) x3Array.getArray();
                    quiz.setQuestionIDsValueX3(new ArrayList<>(List.of(x3Values)));
                }

                String getQuestionsForQuizSQL = "SELECT question_id FROM public.\"quiz_questions\" WHERE quiz_id = ?";
                try (PreparedStatement psQuestions = connection.prepareStatement(getQuestionsForQuizSQL)) {
                    psQuestions.setInt(1, quiz.getId());
                    try (ResultSet questionRS = psQuestions.executeQuery()) {
                        List<Question> questions = new ArrayList<>();
                        while (questionRS.next()) {
                            int questionId = questionRS.getInt("question_id");

                            Optional<Question> questionOpt = questionService.getQuestionById(questionId);
                            questionOpt.ifPresent(questions::add);
                        }
                        quiz.setQuestions(questions);
                    }
                }

                quizList.add(quiz);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all quizzes", e);
        }
        return quizList;
    }


    public List<Quiz> getAllByProfessorId(int professorId) {
        String findAllQuizByProfessorIdSQL = "SELECT * FROM public.\"quizzes\" WHERE professor_id = ?";
        List<Quiz> quizList = new ArrayList<>();

        try (PreparedStatement psFindAllQuizByProfessorId = connection.prepareStatement(findAllQuizByProfessorIdSQL)) {
            psFindAllQuizByProfessorId.setInt(1, professorId);
            try (ResultSet rs = psFindAllQuizByProfessorId.executeQuery()) {
                while (rs.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setId(rs.getInt("id"));
                    quiz.setRuleID(rs.getInt("rule_id"));
                    quiz.setProfessorID(rs.getInt("professor_id"));
                    quiz.setAttempts(rs.getInt("attempts"));
                    quiz.setAverageRating(rs.getDouble("average_rating"));

                    Array x2Array = rs.getArray("question_ids_value_x2");
                    if (x2Array != null) {
                        Integer[] x2Values = (Integer[]) x2Array.getArray();
                        quiz.setQuestionIDsValueX2(new ArrayList<>(List.of(x2Values)));
                    }

                    Array x3Array = rs.getArray("question_ids_value_x3");
                    if (x3Array != null) {
                        Integer[] x3Values = (Integer[]) x3Array.getArray();
                        quiz.setQuestionIDsValueX3(new ArrayList<>(List.of(x3Values)));
                    }

                    quizList.add(quiz);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all quizzes for professor with ID: " + professorId, e);
        }
        return quizList;
    }

}
