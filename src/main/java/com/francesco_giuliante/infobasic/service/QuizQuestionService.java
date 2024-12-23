package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.QuizQuestionDAO;
import com.francesco_giuliante.infobasic.model.QuizQuestion;

import java.util.List;

public class QuizQuestionService {
    private final QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();

    public QuizQuestion save(QuizQuestion quizQuestion) {
        return quizQuestionDAO.save(quizQuestion);
    }

    public void delete(int quizId, int questionId) {
        quizQuestionDAO.delete(quizId, questionId);
    }

    public QuizQuestion update(QuizQuestion quizQuestion, int quizId, int questionId) {
        return quizQuestionDAO.update(quizQuestion, quizId, questionId);
    }

    public List<QuizQuestion> findAll() {
        return quizQuestionDAO.findAll();
    }

    public List<QuizQuestion> findByQuizId(int quizId) {
        return quizQuestionDAO.findByQuizId(quizId);
    }
}
