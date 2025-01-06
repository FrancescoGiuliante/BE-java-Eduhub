package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.service.SubjectProfessorService;
import com.francesco_giuliante.infobasic.model.SubjectProfessor;

import io.javalin.Javalin;

public class SubjectProfessorController {

    private final SubjectProfessorService subjectProfessorService = new SubjectProfessorService();

    public void registerRoutes(Javalin app) {
        app.post("/subject-professor", ctx -> {
            SubjectProfessor subjectProfessor = ctx.bodyAsClass(SubjectProfessor.class);
            subjectProfessorService.assignProfessorToSubject(subjectProfessor.getSubjectID(), subjectProfessor.getProfessorID());
            ctx.status(201).json(subjectProfessor);
        });

        app.delete("/subject-professor/{subjectId}/{professorId}", ctx -> {
            int subjectId = Integer.parseInt(ctx.pathParam("subjectId"));
            int professorId = Integer.parseInt(ctx.pathParam("professorId"));
            subjectProfessorService.removeProfessorFromSubject(subjectId, professorId);
            ctx.status(200).json("Professor removed from subject");
        });

        app.get("/subject-professor/{subjectId}", ctx -> {
            int subjectId = Integer.parseInt(ctx.pathParam("subjectId"));
            ctx.json(subjectProfessorService.getProfessorsBySubjectId(subjectId));
        });
    }
}
