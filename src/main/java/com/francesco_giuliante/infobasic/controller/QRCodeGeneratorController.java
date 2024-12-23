package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.service.LessonParticipationService;
import com.francesco_giuliante.infobasic.utility.QRcode.QRCodeGenerator;

import io.javalin.Javalin;


public class QRCodeGeneratorController {

    private final LessonParticipationService lessonParticipationService = new LessonParticipationService();

    public void registerRoutes(Javalin app) {
        app.get("/qr-code/generate/{lessonID}", ctx -> {
            int lessonId = Integer.parseInt(ctx.pathParam("lessonID"));

            try {
                String base64QRCode = QRCodeGenerator.generateQRCodeBase64(lessonId);
                ctx.json(base64QRCode);
            } catch (Exception e) {
                ctx.status(500).json("Failed to generate QR-Code");
            }
        });

    }
}
