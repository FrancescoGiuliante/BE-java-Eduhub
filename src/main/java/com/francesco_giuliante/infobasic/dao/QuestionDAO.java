package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Question;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDAO implements GenericDAO<Question> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public Question save(Question question) {
        String insertQuestionSQL = "INSERT INTO public.\"questions\"(prompt, answers, correct_answer, wrongs, rights, professor_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement psInsertQuestion = connection.prepareStatement(insertQuestionSQL, Statement.RETURN_GENERATED_KEYS);
            psInsertQuestion.setString(1, question.getPrompt());
            psInsertQuestion.setArray(2, connection.createArrayOf("VARCHAR", question.getAnswers().toArray(new String[0])));
            psInsertQuestion.setString(3, question.getCorrectAnswer());
            psInsertQuestion.setInt(4, question.getWrongs());
            psInsertQuestion.setInt(5, question.getRights());
            psInsertQuestion.setInt(6, question.getProfessorID());

            psInsertQuestion.executeUpdate();

            ResultSet generatedKeys = psInsertQuestion.getGeneratedKeys();
            if (generatedKeys.next()) {
                int questionId = generatedKeys.getInt(1);
                question.setId(questionId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted question.");
            }

            return question;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteQuestionByIdSQL = "DELETE FROM public.\"questions\" WHERE id = ?";

        try {
            PreparedStatement psDeleteQuestionById = connection.prepareStatement(deleteQuestionByIdSQL);
            psDeleteQuestionById.setInt(1, id);
            psDeleteQuestionById.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Question update(Question question, int id) {
        String updateQuestionSQL = "UPDATE public.\"questions\" SET prompt = ?, answers = ?, correct_answer = ?, wrongs = ?, rights = ? WHERE id = ?";

        try (PreparedStatement psUpdateQuestion = connection.prepareStatement(updateQuestionSQL)) {
            psUpdateQuestion.setString(1, question.getPrompt());
            psUpdateQuestion.setArray(2, connection.createArrayOf("VARCHAR", question.getAnswers().toArray(new String[0])));
            psUpdateQuestion.setString(3, question.getCorrectAnswer());
            psUpdateQuestion.setInt(4, question.getWrongs());
            psUpdateQuestion.setInt(5, question.getRights());
            psUpdateQuestion.setInt(6, id);

            int rowsAffected = psUpdateQuestion.executeUpdate();
            if (rowsAffected > 0) {
                question.setId(id);
                return question;
            } else {
                throw new SQLException("No question found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating question with ID: " + id, e);
        }
    }

    @Override
    public Optional<Question> findById(int id) {
        String findQuestionByIdSQL = "SELECT * FROM public.\"questions\" WHERE id = ?";

        try (PreparedStatement psFindQuestion = connection.prepareStatement(findQuestionByIdSQL)) {
            psFindQuestion.setInt(1, id);

            try (ResultSet rs = psFindQuestion.executeQuery()) {
                if (rs.next()) {
                    Question question = new Question(
                            rs.getInt("id"),
                            rs.getString("prompt"),
                            List.of((String[]) rs.getArray("answers").getArray()),
                            rs.getString("correct_answer"),
                            rs.getInt("wrongs"),
                            rs.getInt("rights"),
                            rs.getInt("professor_id")
                    );

                    return Optional.of(question);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding question with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Question> findAll() {
        String findAllQuestionsSQL = "SELECT * FROM public.\"questions\"";
        List<Question> questionList = new ArrayList<>();

        try (PreparedStatement psFindAllQuestions = connection.prepareStatement(findAllQuestionsSQL);
             ResultSet rs = psFindAllQuestions.executeQuery()) {
            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("id"),
                        rs.getString("prompt"),
                        List.of((String[]) rs.getArray("answers").getArray()),
                        rs.getString("correct_answer"),
                        rs.getInt("wrongs"),
                        rs.getInt("rights"),
                        rs.getInt("professor_id")
                );

                questionList.add(question);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all questions", e);
        }

        return questionList;
    }

    public List<Question> findAllByProfessorID(int professorID) {
        String findAllQuestionsByProfessorSQL = "SELECT * FROM public.\"questions\" WHERE professor_id = ?";
        List<Question> questionList = new ArrayList<>();

        try (PreparedStatement psFindAllQuestions = connection.prepareStatement(findAllQuestionsByProfessorSQL)) {
            psFindAllQuestions.setInt(1, professorID);  // Imposta il professorID nella query

            try (ResultSet rs = psFindAllQuestions.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question(
                            rs.getInt("id"),
                            rs.getString("prompt"),
                            List.of((String[]) rs.getArray("answers").getArray()),
                            rs.getString("correct_answer"),
                            rs.getInt("wrongs"),
                            rs.getInt("rights"),
                            rs.getInt("professor_id")
                    );
                    questionList.add(question);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding questions for professor ID: " + professorID, e);
        }

        return questionList;
    }

    public void incrementRights(int questionId) {
        String sql = "UPDATE public.\"questions\" SET rights = rights + 1 WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while incrementing rights for question with ID: " + questionId, e);
        }
    }

    public void incrementWrongs(int questionId) {
        String sql = "UPDATE public.\"questions\" SET wrongs = wrongs + 1 WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while incrementing wrongs for question with ID: " + questionId, e);
        }
    }
}
