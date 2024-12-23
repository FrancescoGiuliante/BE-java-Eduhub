package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Quiz;
import com.francesco_giuliante.infobasic.model.Vote;
import com.francesco_giuliante.infobasic.service.QuizService;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class QuizController {
    private static QuizService quizService = new QuizService();

    public void registerRoutes(Javalin app) {

        app.get("/quizzes", ctx -> {
            ctx.json(quizService.getAllQuizzes());
        });

        app.post("/quiz", ctx -> {
            Quiz quiz = ctx.bodyAsClass(Quiz.class);
            quizService.createQuiz(quiz);
            ctx.status(201).json(quiz);
        });

        app.get("/quiz/{id}", ctx -> {
            int quizId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(quizService.getQuizById(quizId).orElse(null));
        });

        app.get("/professor-quizzes/{professorId}", ctx -> {
            int professorId = Integer.parseInt(ctx.pathParam("professorId"));
            ctx.json(quizService.getAllByProfessorId(professorId));
        });

        app.delete("/quiz/{id}", ctx -> {
            int quizId = Integer.parseInt(ctx.pathParam("id"));
            quizService.deleteQuiz(quizId);
            ctx.status(200).json("Quiz deleted");
        });

        app.put("/quiz/{id}", ctx -> {
            int quizId = Integer.parseInt(ctx.pathParam("id"));
            Quiz quiz2Update = ctx.bodyAsClass(Quiz.class);

            if (quizService.updateQuiz(quiz2Update, quizId) != null) {
                ctx.status(200).json(quiz2Update);
            } else {
                ctx.status(404).json("Quiz not found");
            }
        });

        app.post("/evaluate-quiz/{quizID}/{studentID}", ctx -> {
            HashMap<String, String> studentAnswers = ctx.bodyAsClass(HashMap.class);
            HashMap<Integer, String> studentAnswersIntegerKeys = new HashMap<>();

            for (Map.Entry<String, String> entry : studentAnswers.entrySet()) {
                try {
                    Integer questionId = Integer.parseInt(entry.getKey());
                    studentAnswersIntegerKeys.put(questionId, entry.getValue());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ctx.status(400).json("Invalid question ID format");
                    return;
                }
            }

            try {
                int quizID = Integer.parseInt(ctx.pathParam("quizID"));
                int studentID = Integer.parseInt(ctx.pathParam("studentID"));
                Vote vote = quizService.evaluateQuiz(studentAnswersIntegerKeys, quizID, studentID);
                ctx.status(201).json(vote);
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400).json("Failed to evaluate the quiz");
            }
        });




    }
}
