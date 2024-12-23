package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.StudentDAO;
import com.francesco_giuliante.infobasic.model.Student;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();

    public Student createStudent(Student student) {
        return studentDAO.save(student);
    }

    public Optional<Student> getStudentById(int id) {
        return studentDAO.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public void deleteStudent(int id) {
        studentDAO.delete(id);
    }

    public Student updateStudent(Student student, int id) {
        return studentDAO.update(student, id);
    }
}
