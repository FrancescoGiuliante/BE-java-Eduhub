package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Rule;
import com.francesco_giuliante.infobasic.service.RuleService;
import io.javalin.Javalin;

public class RuleController {
    private static RuleService ruleService = new RuleService();

    public void registerRoutes(Javalin app) {

        app.get("/rules", ctx -> {
            ctx.json(ruleService.getAllRules());
        });

        app.post("/rule", ctx -> {
            Rule rule = ctx.bodyAsClass(Rule.class);
            ruleService.createRule(rule);
            ctx.status(201).json(rule);
        });

        app.get("/rule/{id}", ctx -> {
            int ruleId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(ruleService.getRuleById(ruleId).orElse(null));
        });

        app.get("/professor-rules/{professorId}", ctx -> {
            int professorId = Integer.parseInt(ctx.pathParam("professorId"));
            ctx.json(ruleService.getAllProfessorRules(professorId));
        });

        app.delete("/rule/{id}", ctx -> {
            int ruleId = Integer.parseInt(ctx.pathParam("id"));
            ruleService.deleteRule(ruleId);
            ctx.status(200).json("Rule deleted");
        });

        app.put("/rule/{id}", ctx -> {
            int ruleId = Integer.parseInt(ctx.pathParam("id"));
            Rule ruleToUpdate = ctx.bodyAsClass(Rule.class);

            if (ruleService.updateRule(ruleToUpdate, ruleId) != null) {
                ctx.status(200).json(ruleToUpdate);
            } else {
                ctx.status(404).json("Rule not found");
            }
        });
    }
}
