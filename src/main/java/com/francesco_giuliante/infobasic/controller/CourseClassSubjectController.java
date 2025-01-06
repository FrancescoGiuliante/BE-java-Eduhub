package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.service.CourseClassSubjectService;
import com.francesco_giuliante.infobasic.model.CourseClassSubject;

import io.javalin.Javalin;

public class CourseClassSubjectController {

    private final CourseClassSubjectService courseClassSubjectService = new CourseClassSubjectService();

    public void registerRoutes(Javalin app) {
        app.post("/course-class-subject", ctx -> {
            CourseClassSubject courseClassSubject = ctx.bodyAsClass(CourseClassSubject.class);
            courseClassSubjectService.assignSubjectToClass(courseClassSubject.getCourseClassID(), courseClassSubject.getSubjectID());
            ctx.status(201).json(courseClassSubject);
        });

        app.delete("/course-class-subject/{courseClassId}/{subjectId}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("courseClassId"));
            int subjectId = Integer.parseInt(ctx.pathParam("subjectId"));
            courseClassSubjectService.removeSubjectFromClass(courseClassId, subjectId);
            ctx.status(200).json("Subject removed from course class");
        });

        app.get("/course-class-subject/{courseClassId}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("courseClassId"));
            ctx.json(courseClassSubjectService.getSubjectsByClassId(courseClassId));
        });
    }
}
