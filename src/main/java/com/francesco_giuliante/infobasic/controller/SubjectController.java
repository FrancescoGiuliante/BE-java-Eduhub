package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Subject;
import com.francesco_giuliante.infobasic.service.SubjectService;
import io.javalin.Javalin;

public class SubjectController {
    private static SubjectService subjectService = new SubjectService();

    public void registerRoutes(Javalin app) {

        app.get("/subjects", ctx -> {
            ctx.json(subjectService.getAllSubjects());
        });

        app.post("/subject", ctx -> {
            Subject subject = ctx.bodyAsClass(Subject.class);
            subjectService.createSubject(subject);
            ctx.status(201).json(subject);
        });

        app.get("/subject/{id}", ctx -> {
            int subjectId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(subjectService.getSubjectById(subjectId).orElse(null));
        });

        app.delete("/subject/{id}", ctx -> {
            int subjectId = Integer.parseInt(ctx.pathParam("id"));
            subjectService.deleteSubject(subjectId);
            ctx.status(200).json("Subject deleted");
        });

        app.put("/subject/{id}", ctx -> {
            int subjectId = Integer.parseInt(ctx.pathParam("id"));
            Subject subject2Update = ctx.bodyAsClass(Subject.class);

            if (subjectService.updateSubject(subject2Update, subjectId) != null) {
                ctx.status(200).json(subject2Update);
            } else {
                ctx.status(404).json("Subject not found");
            }
        });
    }
}
