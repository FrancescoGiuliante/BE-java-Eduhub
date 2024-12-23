package com.francesco_giuliante.infobasic;

import com.francesco_giuliante.infobasic.controller.*;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.reflectClientOrigin = true;
                });
            });
        }).start(8080);


        CourseClassController courseClassController = new CourseClassController();
        courseClassController.registerRoutes(app);

        CourseController courseController = new CourseController();
        courseController.registerRoutes(app);

        LessonController lessonController = new LessonController();
        lessonController.registerRoutes(app);

        LessonParticipationController lessonParticipationController = new LessonParticipationController();
        lessonParticipationController.registerRoutes(app);

        ProfessorController professorController = new ProfessorController();
        professorController.registerRoutes(app);

        QRCodeGeneratorController qrCodeGeneratorController = new QRCodeGeneratorController();
        qrCodeGeneratorController.registerRoutes(app);

        QuestionController questionController = new QuestionController();
        questionController.registerRoutes(app);

        QuizController quizController = new QuizController();
        quizController.registerRoutes(app);

        QuizQuestionController quizQuestionController = new QuizQuestionController();
        quizQuestionController.registerRoutes(app);

        RuleController ruleController = new RuleController();
        ruleController.registerRoutes(app);

        StudentController studentController = new StudentController();
        studentController.registerRoutes(app);

        SubjectController subjectController = new SubjectController();
        subjectController.registerRoutes(app);

        VoteController voteController = new VoteController();
        voteController.registerRoutes(app);

        WeekProgramController weekProgramController = new WeekProgramController();
        weekProgramController.registerRoutes(app);
    }
}