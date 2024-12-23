package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.LessonParticipationDAO;

import java.util.List;

public class LessonParticipationService {
    private final LessonParticipationDAO lessonParticipationDAO = new LessonParticipationDAO();

    public void registerAttendance(int lessonId, int studentId) {
        lessonParticipationDAO.registerAttendance(lessonId, studentId);
    }

    public List<Integer> getStudentsByLessonId(int lessonId) {
        return lessonParticipationDAO.getStudentsByLessonId(lessonId);
    }

    public void deleteAttendance(int lessonId, int studentId) {
        lessonParticipationDAO.deleteAttendance(lessonId, studentId);
    }
}
