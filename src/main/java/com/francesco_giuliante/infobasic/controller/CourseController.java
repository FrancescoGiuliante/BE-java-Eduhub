package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Course;
import com.francesco_giuliante.infobasic.service.CourseService;
import io.javalin.Javalin;

public class CourseController {
    private static CourseService courseService = new CourseService();

    public void registerRoutes(Javalin app) {

        app.get("/courses", ctx -> {
            ctx.json(courseService.getAllCourses());
        });

        app.post("/course", ctx -> {
            Course course = ctx.bodyAsClass(Course.class);
            courseService.createCourse(course);
            ctx.status(201).json(course);
        });

        app.get("/course/{id}", ctx -> {
            int courseId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(courseService.getCourseById(courseId).orElse(null));
        });

        app.delete("/course/{id}", ctx -> {
            int courseId = Integer.parseInt(ctx.pathParam("id"));
            courseService.deleteCourse(courseId);
            ctx.status(200).json("Course deleted");
        });

        app.put("/course/{id}", ctx -> {
            int courseId = Integer.parseInt(ctx.pathParam("id"));
            Course course2Update = ctx.bodyAsClass(Course.class);

            if (courseService.updateCourse(course2Update, courseId) != null) {
                ctx.status(200).json(course2Update);
            } else {
                ctx.status(404).json("Course not found");
            }
        });
    }
}
