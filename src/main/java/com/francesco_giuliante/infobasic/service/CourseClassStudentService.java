package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.CourseClassStudentDAO;

import java.util.List;

public class CourseClassStudentService {
    private final CourseClassStudentDAO courseClassStudentDAO = new CourseClassStudentDAO();

    public void enrollStudentInClass(int courseClassId, int studentId) {
        courseClassStudentDAO.enrollStudentInClass(courseClassId, studentId);
    }

    public List<Integer> getStudentsByClassId(int courseClassId) {
        return courseClassStudentDAO.getStudentsByClassId(courseClassId);
    }

    public void removeStudentFromClass(int courseClassId, int studentId) {
        courseClassStudentDAO.removeStudentFromClass(courseClassId, studentId);
    }
}
