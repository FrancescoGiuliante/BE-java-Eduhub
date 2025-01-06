package com.francesco_giuliante.infobasic.controller;

import com.francesco_giuliante.infobasic.model.Student;
import com.francesco_giuliante.infobasic.service.StudentService;
import io.javalin.Javalin;

public class StudentController {
    private static StudentService studentService = new StudentService();

    public void registerRoutes(Javalin app) {

        app.get("/students", ctx -> {
            ctx.json(studentService.getAllStudents());
        });

        app.post("/student", ctx -> {
            Student student = ctx.bodyAsClass(Student.class);
            studentService.createStudent(student);
            ctx.status(201).json(student);
        });

        app.get("/student/{id}", ctx -> {
            int studentId = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(studentService.getStudentById(studentId).orElse(null));
        });

        app.get("/student/user/{userId}", ctx -> {
            int studentUserId = Integer.parseInt(ctx.pathParam("userId"));
            ctx.json(studentService.getStudentByUserId(studentUserId).orElse(null));
        });

        app.delete("/student/{id}", ctx -> {
            int studentId = Integer.parseInt(ctx.pathParam("id"));
            studentService.deleteStudent(studentId);
            ctx.status(200).json("Student deleted");
        });

        app.put("/student/{userID}", ctx -> {
            int studentUserId = Integer.parseInt(ctx.pathParam("userID"));
            Student student2Update = ctx.bodyAsClass(Student.class);

            if (studentService.updateStudent(student2Update, studentUserId) != null) {
                ctx.status(200).json(student2Update);
            } else {
                ctx.status(404).json("Student not found");
            }
        });
    }
}

