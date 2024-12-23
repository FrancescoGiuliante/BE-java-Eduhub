package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.QuizQuestion;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizQuestionDAO {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public QuizQuestion save(QuizQuestion quizQuestion) {
        String insertQuizQuestionSQL = "INSERT INTO public.\"quiz_questions\"(quiz_id, question_id) VALUES (?, ?)";

        try {
            PreparedStatement psInsertQuizQuestion = connection.prepareStatement(insertQuizQuestionSQL);
            psInsertQuizQuestion.setInt(1, quizQuestion.getQuizID());
            psInsertQuizQuestion.setInt(2, quizQuestion.getQuestionID());

            psInsertQuizQuestion.executeUpdate();

            return quizQuestion;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int quizId, int questionId) {
        String deleteQuizQuestionSQL = "DELETE FROM public.\"quiz_questions\" WHERE quiz_id = ? AND question_id = ?";

        try {
            PreparedStatement psDeleteQuizQuestion = connection.prepareStatement(deleteQuizQuestionSQL);
            psDeleteQuizQuestion.setInt(1, quizId);
            psDeleteQuizQuestion.setInt(2, questionId);
            psDeleteQuizQuestion.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public QuizQuestion update(QuizQuestion quizQuestion, int quizId, int questionId) {
        String updateQuizQuestionSQL = "UPDATE public.\"quiz_questions\" SET quiz_id = ?, question_id = ? WHERE quiz_id = ? AND question_id = ?";

        try (PreparedStatement psUpdateQuizQuestion = connection.prepareStatement(updateQuizQuestionSQL)) {
            psUpdateQuizQuestion.setInt(1, quizQuestion.getQuizID());
            psUpdateQuizQuestion.setInt(2, quizQuestion.getQuestionID());
            psUpdateQuizQuestion.setInt(3, quizId);
            psUpdateQuizQuestion.setInt(4, questionId);

            psUpdateQuizQuestion.executeUpdate();

            return quizQuestion;
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating quiz-question", e);
        }
    }

    public List<QuizQuestion> findAll() {
        String findAllQuizQuestionsSQL = "SELECT * FROM public.\"quiz_questions\"";
        List<QuizQuestion> quizQuestionList = new ArrayList<>();

        try (PreparedStatement psFindAllQuizQuestions = connection.prepareStatement(findAllQuizQuestionsSQL);
             ResultSet rs = psFindAllQuizQuestions.executeQuery()) {
            while (rs.next()) {
                QuizQuestion quizQuestion = new QuizQuestion(
                        rs.getInt("quiz_id"),
                        rs.getInt("question_id")
                );
                quizQuestionList.add(quizQuestion);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all quiz-questions", e);
        }
        return quizQuestionList;
    }

    public List<QuizQuestion> findByQuizId(int quizId) {
        String findQuizQuestionsByQuizIdSQL = "SELECT * FROM public.\"quiz_questions\" WHERE quiz_id = ?";
        List<QuizQuestion> quizQuestionList = new ArrayList<>();

        try (PreparedStatement psFindQuizQuestionsByQuizId = connection.prepareStatement(findQuizQuestionsByQuizIdSQL)) {
            psFindQuizQuestionsByQuizId.setInt(1, quizId);

            try (ResultSet rs = psFindQuizQuestionsByQuizId.executeQuery()) {
                while (rs.next()) {
                    QuizQuestion quizQuestion = new QuizQuestion(
                            rs.getInt("quiz_id"),
                            rs.getInt("question_id")
                    );
                    quizQuestionList.add(quizQuestion);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding quiz-questions for quiz ID: " + quizId, e);
        }
        return quizQuestionList;
    }
}
