package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.service.CourseClassProfessorService;
import com.francesco_giuliante.infobasic.model.CourseClassProfessor;

import io.javalin.Javalin;

public class CourseClassProfessorController {

    private final CourseClassProfessorService courseClassProfessorService = new CourseClassProfessorService();

    public void registerRoutes(Javalin app) {
        app.post("/course-class-professor", ctx -> {
            CourseClassProfessor courseClassProfessor = ctx.bodyAsClass(CourseClassProfessor.class);
            courseClassProfessorService.assignProfessorToClass(courseClassProfessor.getCourseClassID(), courseClassProfessor.getProfessorID());
            ctx.status(201).json(courseClassProfessor);
        });

        app.delete("/course-class-professor/{courseClassId}/{professorId}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("courseClassId"));
            int professorId = Integer.parseInt(ctx.pathParam("professorId"));
            courseClassProfessorService.removeProfessorFromClass(courseClassId, professorId);
            ctx.status(200).json("Professor removed from course class");
        });

        app.get("/course-class-professor/{courseClassId}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("courseClassId"));
            ctx.json(courseClassProfessorService.getProfessorsByClassId(courseClassId));
        });
    }
}
