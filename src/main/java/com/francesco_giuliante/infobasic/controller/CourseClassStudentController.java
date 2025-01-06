package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.service.CourseClassStudentService;
import com.francesco_giuliante.infobasic.model.CourseClassStudent;

import io.javalin.Javalin;

public class CourseClassStudentController {

    private final CourseClassStudentService courseClassStudentService = new CourseClassStudentService();

    public void registerRoutes(Javalin app) {
        app.post("/course-class-student", ctx -> {
            CourseClassStudent courseClassStudent = ctx.bodyAsClass(CourseClassStudent.class);
            courseClassStudentService.enrollStudentInClass(courseClassStudent.getCourseClassID(), courseClassStudent.getStudentID());
            ctx.status(201).json(courseClassStudent);
        });

        app.delete("/course-class-student/{courseClassId}/{studentId}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("courseClassId"));
            int studentId = Integer.parseInt(ctx.pathParam("studentId"));
            courseClassStudentService.removeStudentFromClass(courseClassId, studentId);
            ctx.status(200).json("Student removed from course class");
        });

        app.get("/course-class-student/{courseClassId}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("courseClassId"));
            ctx.json(courseClassStudentService.getStudentsByClassId(courseClassId));
        });
    }
}
