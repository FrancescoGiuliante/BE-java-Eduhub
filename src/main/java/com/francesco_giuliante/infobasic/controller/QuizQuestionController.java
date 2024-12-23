package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.QuizQuestion;
import com.francesco_giuliante.infobasic.service.QuizQuestionService;

import io.javalin.Javalin;

public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService = new QuizQuestionService();

    public void registerRoutes(Javalin app) {
        app.post("/quiz-question", ctx -> {
            QuizQuestion quizQuestion = ctx.bodyAsClass(QuizQuestion.class);
            QuizQuestion savedQuizQuestion = quizQuestionService.save(quizQuestion);
            ctx.status(201).json(savedQuizQuestion);
        });

        app.delete("/quiz-question/{quizId}/{questionId}", ctx -> {
            int quizId = Integer.parseInt(ctx.pathParam("quizId"));
            int questionId = Integer.parseInt(ctx.pathParam("questionId"));
            quizQuestionService.delete(quizId, questionId);
            ctx.status(200).json("QuizQuestion deleted");
        });

        app.put("/quiz-question/{quizId}/{questionId}", ctx -> {
            int quizId = Integer.parseInt(ctx.pathParam("quizId"));
            int questionId = Integer.parseInt(ctx.pathParam("questionId"));
            QuizQuestion quizQuestion = ctx.bodyAsClass(QuizQuestion.class);
            QuizQuestion updatedQuizQuestion = quizQuestionService.update(quizQuestion, quizId, questionId);
            ctx.status(200).json(updatedQuizQuestion);
        });

        app.get("/quiz-question", ctx -> {
            ctx.json(quizQuestionService.findAll());
        });

        app.get("/quiz-question/quiz/{quizId}", ctx -> {
            int quizId = Integer.parseInt(ctx.pathParam("quizId"));
            ctx.json(quizQuestionService.findByQuizId(quizId));
        });
    }
}
