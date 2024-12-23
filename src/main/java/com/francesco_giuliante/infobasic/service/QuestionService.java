package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.QuestionDAO;
import com.francesco_giuliante.infobasic.model.Question;

import java.util.List;
import java.util.Optional;

public class QuestionService {
    private final QuestionDAO questionDAO = new QuestionDAO();

    public Question createQuestion(Question question) {
        return questionDAO.save(question);
    }

    public Optional<Question> getQuestionById(int id) {
        return questionDAO.findById(id);
    }

    public List<Question> getAllQuestions() {
        return questionDAO.findAll();
    }

    public List<Question> getAllQuestionsByProfessorId(int professorID) {
        return questionDAO.findAllByProfessorID(professorID);
    }

    public void deleteQuestion(int id) {
        questionDAO.delete(id);
    }

    public Question updateQuestion(Question question, int id) {
        return questionDAO.update(question, id);
    }

    public void incrementRights(int questionId) {
        questionDAO.incrementRights(questionId);
    }

    public void incrementWrongs(int questionId) {
        questionDAO.incrementWrongs(questionId);
    }
}
