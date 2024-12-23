package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.CourseClassDAO;
import com.francesco_giuliante.infobasic.model.CourseClass;

import java.util.List;
import java.util.Optional;

public class CourseClassService {
    private final CourseClassDAO courseClassDAO = new CourseClassDAO();

    public CourseClass createCourseClass(CourseClass courseClass) {
        return courseClassDAO.save(courseClass);
    }

    public Optional<CourseClass> getCourseClassById(int id) {
        return courseClassDAO.findById(id);
    }

    public List<CourseClass> getAllCourseClasses() {
        return courseClassDAO.findAll();
    }

    public void deleteCourseClass(int id) {
        courseClassDAO.delete(id);
    }

    public CourseClass updateCourseClass(CourseClass courseClass, int id) {
        return courseClassDAO.update(courseClass, id);
    }
}
