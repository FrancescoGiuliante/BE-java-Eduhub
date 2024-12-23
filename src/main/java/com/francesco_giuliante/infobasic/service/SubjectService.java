package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.SubjectDAO;
import com.francesco_giuliante.infobasic.model.Subject;

import java.util.List;
import java.util.Optional;

public class SubjectService {
    private final SubjectDAO subjectDAO = new SubjectDAO();

    public Subject createSubject(Subject subject) {
        return subjectDAO.save(subject);
    }

    public Optional<Subject> getSubjectById(int id) {
        return subjectDAO.findById(id);
    }

    public List<Subject> getAllSubjects() {
        return subjectDAO.findAll();
    }

    public void deleteSubject(int id) {
        subjectDAO.delete(id);
    }

    public Subject updateSubject(Subject subject, int id) {
        return subjectDAO.update(subject, id);
    }
}
