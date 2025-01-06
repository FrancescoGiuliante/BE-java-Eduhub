package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.CourseClassProfessorDAO;

import java.util.List;

public class CourseClassProfessorService {
    private final CourseClassProfessorDAO courseClassProfessorDAO = new CourseClassProfessorDAO();

    public void assignProfessorToClass(int courseClassId, int professorId) {
        courseClassProfessorDAO.assignProfessorToClass(courseClassId, professorId);
    }

    public List<Integer> getProfessorsByClassId(int courseClassId) {
        return courseClassProfessorDAO.getProfessorsByClassId(courseClassId);
    }

    public void removeProfessorFromClass(int courseClassId, int professorId) {
        courseClassProfessorDAO.removeProfessorFromClass(courseClassId, professorId);
    }
}

