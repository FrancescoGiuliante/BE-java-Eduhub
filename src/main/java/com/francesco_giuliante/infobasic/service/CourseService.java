package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.CourseDAO;
import com.francesco_giuliante.infobasic.model.Course;

import java.util.List;
import java.util.Optional;

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAO();

    public Course createCourse(Course course) {
        return courseDAO.save(course);
    }

    public Optional<Course> getCourseById(int id) {
        return courseDAO.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public void deleteCourse(int id) {
        courseDAO.delete(id);
    }

    public Course updateCourse(Course course, int id) {
        return courseDAO.update(course, id);
    }
}
