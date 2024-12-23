package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.QuizDAO;
import com.francesco_giuliante.infobasic.model.Question;
import com.francesco_giuliante.infobasic.model.Quiz;
import com.francesco_giuliante.infobasic.model.Rule;
import com.francesco_giuliante.infobasic.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class QuizService {
    private RuleService ruleService = new RuleService();
    private QuestionService questionService = new QuestionService();
    private VoteService voteService = new VoteService();
    private final QuizDAO quizDAO = new QuizDAO();

    public Quiz createQuiz(Quiz quiz) {
        return quizDAO.save(quiz);
    }

    public Optional<Quiz> getQuizById(int id) {
        return quizDAO.findById(id);
    }

    public List<Quiz> getAllQuizzes() {
        return quizDAO.findAll();
    }

    public List<Quiz> getAllByProfessorId(int professorID) {
        return quizDAO.getAllByProfessorId(professorID);
    }

    public void deleteQuiz(int id) {
        quizDAO.delete(id);
    }

    public Quiz updateQuiz(Quiz quiz, int id) {
        return quizDAO.update(quiz, id);
    }

    private HashMap<Integer, String> getAllCorrectAnswers(List<Question> questions) {
        HashMap<Integer, String> correctAnswers = new HashMap<>();
        for (int i = 0; i < questions.size(); i++) {
            correctAnswers.put(i, questions.get(i).getCorrectAnswer());
        }

        return correctAnswers;
    }


    private double getBasePoints(Quiz quiz) {
        Rule rule = ruleService.getRuleById(quiz.getRuleID())
                .orElseThrow(() -> new RuntimeException("Rule not found for Rule ID: " + quiz.getRuleID()));
        return rule.getValueRightAnswer();
    }

    private double getWrongPoints(Quiz quiz) {
        Rule rule = ruleService.getRuleById(quiz.getRuleID())
                .orElseThrow(() -> new RuntimeException("Rule not found for Rule ID: " + quiz.getRuleID()));
        return rule.getValueWrongAnswer();
    }

    private double scoreCalculation(HashMap<Integer, String> studentAnswers, HashMap<Integer, String> correctAnswers, double basePoint, double wrongPoint, List<Integer> questionsX2, List<Integer> questionsX3) {
        double score = 0.0;

        for (Integer questionId : studentAnswers.keySet()) {
            String studentAnswer = studentAnswers.get(questionId);
            String correctAnswer = correctAnswers.get(questionId);

            if (studentAnswer.equals(correctAnswer)) {
                questionService.incrementRights(questionId);
                if (Arrays.asList(questionsX2).contains(questionId)) {
                    score += basePoint * 2;
                } else if (Arrays.asList(questionsX3).contains(questionId)) {
                    score += basePoint * 3;
                } else {
                    score += basePoint;
                }
            } else {
                questionService.incrementWrongs(questionId);
                score -= wrongPoint;
            }
        }

        return score;
    }


    public Vote evaluateQuiz(HashMap<Integer, String> studentAswers, int quizID, int studentID) {
        Quiz quiz = quizDAO.findById(quizID).orElseThrow(() -> new RuntimeException("Quiz not found for ID: " + quizID));
        List<Question> questions = quiz.getQuestions();

        HashMap<Integer, String> correctAnswers = getAllCorrectAnswers(questions);

        double score = scoreCalculation(studentAswers, correctAnswers, getBasePoints(quiz), getWrongPoints(quiz), quiz.getQuestionIDsValueX2(), quiz.getQuestionIDsValueX3());

        int attempts = quiz.getAttempts() + 1;
        double newAverage = (quiz.getAverageRating() * (attempts - 1) + score) / attempts;

        quiz.setAttempts(attempts);
        quiz.setAverageRating(newAverage);

        quizDAO.update(quiz, quiz.getId());

        Vote vote = new Vote();
        vote.setQuizID(quiz.getId());
        vote.setStudentID(studentID);
        vote.setDate(LocalDate.now());
        vote.setResult(score);

        voteService.createVote(vote);

        return vote;
    }
}
