package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.CourseClassSubjectDAO;

import java.util.List;

public class CourseClassSubjectService {
    private final CourseClassSubjectDAO courseClassSubjectDAO = new CourseClassSubjectDAO();

    public void assignSubjectToClass(int courseClassId, int subjectId) {
        courseClassSubjectDAO.assignSubjectToClass(courseClassId, subjectId);
    }

    public List<Integer> getSubjectsByClassId(int courseClassId) {
        return courseClassSubjectDAO.getSubjectsByClassId(courseClassId);
    }

    public void removeSubjectFromClass(int courseClassId, int subjectId) {
        courseClassSubjectDAO.removeSubjectFromClass(courseClassId, subjectId);
    }
}
