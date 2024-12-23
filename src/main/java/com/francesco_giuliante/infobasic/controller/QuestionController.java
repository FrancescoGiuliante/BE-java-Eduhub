package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Question;
import com.francesco_giuliante.infobasic.service.QuestionService;
import io.javalin.Javalin;

public class QuestionController {
    private static QuestionService questionService = new QuestionService();

    public void registerRoutes(Javalin app) {

        app.get("/questions", ctx -> {
            ctx.json(questionService.getAllQuestions());
        });

        app.post("/question", ctx -> {
            Question question = ctx.bodyAsClass(Question.class);
            questionService.createQuestion(question);
            ctx.status(201).json(question);
        });

        app.get("/question/{id}", ctx -> {
            int questionId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(questionService.getQuestionById(questionId).orElse(null));
        });

        app.get("/questions-professor/{id}", ctx -> {
            int questionId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(questionService.getAllQuestionsByProfessorId(questionId));
        });

        app.delete("/question/{id}", ctx -> {
            int questionId = Integer.parseInt(ctx.pathParam("id"));
            questionService.deleteQuestion(questionId);
            ctx.status(200).json("Question deleted");
        });

        app.put("/question/{id}", ctx -> {
            int questionId = Integer.parseInt(ctx.pathParam("id"));
            Question question2Update = ctx.bodyAsClass(Question.class);

            if (questionService.updateQuestion(question2Update, questionId) != null) {
                ctx.status(200).json(question2Update);
            } else {
                ctx.status(404).json("Question not found");
            }
        });
    }
}

