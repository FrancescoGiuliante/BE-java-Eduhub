package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.WeekProgram;
import com.francesco_giuliante.infobasic.service.WeekProgramService;
import io.javalin.Javalin;

public class WeekProgramController {
    private static WeekProgramService weekProgramService = new WeekProgramService();

    public void registerRoutes(Javalin app) {

        app.get("/weekprograms", ctx -> {
            ctx.json(weekProgramService.getAllWeekPrograms());
        });

        app.post("/weekprogram", ctx -> {
            WeekProgram weekProgram = ctx.bodyAsClass(WeekProgram.class);
            weekProgramService.createWeekProgram(weekProgram);
            ctx.status(201).json(weekProgram);
        });

        app.get("/weekprogram/{id}", ctx -> {
            int weekProgramId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(weekProgramService.getWeekProgramById(weekProgramId).orElse(null));
        });

        app.delete("/weekprogram/{id}", ctx -> {
            int weekProgramId = Integer.parseInt(ctx.pathParam("id"));
            weekProgramService.deleteWeekProgram(weekProgramId);
            ctx.status(200).json("WeekProgram deleted");
        });

        app.put("/weekprogram/{id}", ctx -> {
            int weekProgramId = Integer.parseInt(ctx.pathParam("id"));
            WeekProgram weekProgram2Update = ctx.bodyAsClass(WeekProgram.class);

            if (weekProgramService.updateWeekProgram(weekProgram2Update, weekProgramId) != null) {
                ctx.status(200).json(weekProgram2Update);
            } else {
                ctx.status(404).json("WeekProgram not found");
            }
        });
    }
}
