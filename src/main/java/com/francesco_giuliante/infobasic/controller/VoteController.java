package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Vote;
import com.francesco_giuliante.infobasic.service.VoteService;
import io.javalin.Javalin;

public class VoteController {
    private static VoteService voteService = new VoteService();

    public void registerRoutes(Javalin app) {

        app.get("/votes", ctx -> {
            ctx.json(voteService.getAllVotes());
        });

        app.post("/vote", ctx -> {
            Vote vote = ctx.bodyAsClass(Vote.class);
            voteService.createVote(vote);
            ctx.status(201).json(vote);
        });

        app.get("/vote/{id}", ctx -> {
            int voteId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(voteService.getVoteById(voteId).orElse(null));
        });

        app.delete("/vote/{id}", ctx -> {
            int voteId = Integer.parseInt(ctx.pathParam("id"));
            voteService.deleteVote(voteId);
            ctx.status(200).json("Vote deleted");
        });

        app.put("/vote/{id}", ctx -> {
            int voteId = Integer.parseInt(ctx.pathParam("id"));
            Vote vote2Update = ctx.bodyAsClass(Vote.class);

            if (voteService.updateVote(vote2Update, voteId) != null) {
                ctx.status(200).json(vote2Update);
            } else {
                ctx.status(404).json("Vote not found");
            }
        });
    }
}

