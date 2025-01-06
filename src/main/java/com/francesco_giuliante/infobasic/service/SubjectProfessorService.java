package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.SubjectProfessorDAO;

import java.util.List;

public class SubjectProfessorService {
    private final SubjectProfessorDAO subjectProfessorDAO = new SubjectProfessorDAO();

    public void assignProfessorToSubject(int subjectId, int professorId) {
        subjectProfessorDAO.assignProfessorToSubject(subjectId, professorId);
    }

    public List<Integer> getProfessorsBySubjectId(int subjectId) {
        return subjectProfessorDAO.getProfessorsBySubjectId(subjectId);
    }

    public void removeProfessorFromSubject(int subjectId, int professorId) {
        subjectProfessorDAO.removeProfessorFromSubject(subjectId, professorId);
    }
}
