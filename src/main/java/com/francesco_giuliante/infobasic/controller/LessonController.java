package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Lesson;
import com.francesco_giuliante.infobasic.service.LessonService;
import io.javalin.Javalin;

public class LessonController {
    private static LessonService lessonService = new LessonService();

    public void registerRoutes(Javalin app) {

        app.get("/lessons", ctx -> {
            ctx.json(lessonService.getAllLessons());
        });

        app.get("/today-lessons", ctx -> {
            ctx.json(lessonService.getAllTodayLessons());
        });

        app.post("/lesson", ctx -> {
            Lesson lesson = ctx.bodyAsClass(Lesson.class);
            lessonService.createLesson(lesson);
            ctx.status(201).json(lesson);
        });

        app.get("/lesson/{id}", ctx -> {
            int lessonId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(lessonService.getLessonById(lessonId).orElse(null));
        });

        app.delete("/lesson/{id}", ctx -> {
            int lessonId = Integer.parseInt(ctx.pathParam("id"));
            lessonService.deleteLesson(lessonId);
            ctx.status(200).json("Lesson deleted");
        });

        app.put("/lesson/{id}", ctx -> {
            int lessonId = Integer.parseInt(ctx.pathParam("id"));
            Lesson lesson2Update = ctx.bodyAsClass(Lesson.class);

            if (lessonService.updateLesson(lesson2Update, lessonId) != null) {
                ctx.status(200).json(lesson2Update);
            } else {
                ctx.status(404).json("Lesson not found");
            }
        });
    }
}
