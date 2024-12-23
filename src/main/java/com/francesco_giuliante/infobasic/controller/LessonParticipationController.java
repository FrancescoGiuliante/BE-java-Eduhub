package com.francesco_giuliante.infobasic.controller;


import com.francesco_giuliante.infobasic.model.LessonParticipation;
import com.francesco_giuliante.infobasic.service.LessonParticipationService;
import io.javalin.Javalin;

public class LessonParticipationController {
    private static LessonParticipationService lessonParticipationService = new LessonParticipationService();

    public void registerRoutes(Javalin app) {

        app.get("/attendance/{lessonId}", ctx -> {
            int lessonId = Integer.parseInt(ctx.pathParam("lessonId"));
            ctx.json(lessonParticipationService.getStudentsByLessonId(lessonId));
        });

        app.post("/attendance/register", ctx -> {
            LessonParticipation lessonParticipation = ctx.bodyAsClass(LessonParticipation.class);
            lessonParticipationService.registerAttendance(lessonParticipation.getLessonID(), lessonParticipation.getStudentID());
            ctx.status(201).json("Attendance registered successfully");
        });

        app.delete("/attendance", ctx -> {
            int lessonId = Integer.parseInt(ctx.queryParam("lessonId"));
            int studentId = Integer.parseInt(ctx.queryParam("studentId"));
            lessonParticipationService.deleteAttendance(lessonId, studentId);
            ctx.status(200).json("Attendance deleted successfully");
        });
    }
}