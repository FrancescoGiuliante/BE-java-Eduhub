package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Professor;
import com.francesco_giuliante.infobasic.service.ProfessorService;
import io.javalin.Javalin;

public class ProfessorController {
    private static ProfessorService professorService = new ProfessorService();

    public void registerRoutes(Javalin app) {

        app.get("/professors", ctx -> {
            ctx.json(professorService.getAllProfessors());
        });

        app.post("/professor", ctx -> {
            Professor professor = ctx.bodyAsClass(Professor.class);
            professorService.createProfessor(professor);
            ctx.status(201).json(professor);
        });

        app.get("/professor/{id}", ctx -> {
            int professorId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(professorService.getProfessorById(professorId).orElse(null));
        });

        app.get("/professor/user/{userId}", ctx -> {
            int professorUserId = Integer.parseInt(ctx.pathParam("userId"));
            ctx.json(professorService.getProfessorByUserId(professorUserId).orElse(null));
        });

        app.delete("/professor/{id}", ctx -> {
            int professorId = Integer.parseInt(ctx.pathParam("id"));
            professorService.deleteProfessor(professorId);
            ctx.status(200).json("Professor deleted");
        });

        app.put("/professor/{id}", ctx -> {
            int professorUserId = Integer.parseInt(ctx.pathParam("id"));
            Professor professor2Update = ctx.bodyAsClass(Professor.class);

            if (professorService.updateProfessor(professor2Update, professorUserId) != null) {
                ctx.status(200).json(professor2Update);
            } else {
                ctx.status(404).json("Professor not found");
            }
        });
    }
}

