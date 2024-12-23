package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.LessonDAO;
import com.francesco_giuliante.infobasic.model.Lesson;

import java.util.List;
import java.util.Optional;

public class LessonService {
    private final LessonDAO lessonDAO = new LessonDAO();

    public Lesson createLesson(Lesson lesson) {
        return lessonDAO.save(lesson);
    }

    public Optional<Lesson> getLessonById(int id) {
        return lessonDAO.findById(id);
    }

    public List<Lesson> getAllLessons() {
        return lessonDAO.findAll();
    }

    public void deleteLesson(int id) {
        lessonDAO.delete(id);
    }

    public Lesson updateLesson(Lesson lesson, int id) {
        return lessonDAO.update(lesson, id);
    }
}
