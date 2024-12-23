package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.CourseClass;
import com.francesco_giuliante.infobasic.service.CourseClassService;
import io.javalin.Javalin;

public class CourseClassController {
    private static CourseClassService courseClassService = new CourseClassService();

    public void registerRoutes(Javalin app) {

        app.get("/courseclasses", ctx -> {
            ctx.json(courseClassService.getAllCourseClasses());
        });

        app.post("/courseclass", ctx -> {
            CourseClass courseClass = ctx.bodyAsClass(CourseClass.class);
            courseClassService.createCourseClass(courseClass);
            ctx.status(201).json(courseClass);
        });

        app.get("/courseclass/{id}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(courseClassService.getCourseClassById(courseClassId).orElse(null));
        });

        app.delete("/courseclass/{id}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("id"));
            courseClassService.deleteCourseClass(courseClassId);
            ctx.status(200).json("CourseClass deleted");
        });

        app.put("/courseclass/{id}", ctx -> {
            int courseClassId = Integer.parseInt(ctx.pathParam("id"));
            CourseClass courseClass2Update = ctx.bodyAsClass(CourseClass.class);

            if (courseClassService.updateCourseClass(courseClass2Update, courseClassId) != null) {
                ctx.status(200).json(courseClass2Update);
            } else {
                ctx.status(404).json("CourseClass not found");
            }
        });
    }
}
