package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Lesson;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonDAO implements GenericDAO<Lesson> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public Lesson save(Lesson lesson) {
        String insertLessonSQL = "INSERT INTO public.\"lessons\"(professor_id, subject_id, date, class_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement psInsertLesson = connection.prepareStatement(insertLessonSQL, Statement.RETURN_GENERATED_KEYS);
            psInsertLesson.setInt(1, lesson.getProfessorID());
            psInsertLesson.setInt(2, lesson.getSubjectID());
            psInsertLesson.setDate(3, java.sql.Date.valueOf(lesson.getDate()));
            psInsertLesson.setInt(4, lesson.getClassID());

            psInsertLesson.executeUpdate();

            ResultSet generatedKeys = psInsertLesson.getGeneratedKeys();
            if (generatedKeys.next()) {
                int lessonId = generatedKeys.getInt(1);
                lesson.setId(lessonId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted lesson.");
            }

            return lesson;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteLessonByIdSQL = "DELETE FROM public.\"lessons\" WHERE id = ?";

        try {
            PreparedStatement psDeleteLessonById = connection.prepareStatement(deleteLessonByIdSQL);
            psDeleteLessonById.setInt(1, id);
            psDeleteLessonById.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Lesson update(Lesson lesson, int id) {
        String updateLessonSQL = "UPDATE public.\"lessons\" SET professor_id = ?, subject_id = ?, date = ?, class_id = ? WHERE id = ?";

        try (PreparedStatement psUpdateLesson = connection.prepareStatement(updateLessonSQL)) {
            psUpdateLesson.setInt(1, lesson.getProfessorID());
            psUpdateLesson.setInt(2, lesson.getSubjectID());
            psUpdateLesson.setDate(3, java.sql.Date.valueOf(lesson.getDate()));
            psUpdateLesson.setInt(4, lesson.getClassID());
            psUpdateLesson.setInt(5, id);

            int rowsAffected = psUpdateLesson.executeUpdate();
            if (rowsAffected > 0) {
                lesson.setId(id);
                return lesson;
            } else {
                throw new SQLException("No lesson found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating lesson with ID: " + id, e);
        }
    }

    @Override
    public Optional<Lesson> findById(int id) {
        String findLessonByIdSQL = "SELECT * FROM public.\"lessons\" WHERE id = ?";

        try (PreparedStatement psFindLesson = connection.prepareStatement(findLessonByIdSQL)) {
            psFindLesson.setInt(1, id);

            try (ResultSet rs = psFindLesson.executeQuery()) {
                if (rs.next()) {
                    Lesson lesson = new Lesson(
                            rs.getInt("id"),
                            rs.getInt("professor_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("class_id")
                    );

                    String participationsSQL = "SELECT student_id FROM lesson_participations WHERE lesson_id = ?";
                    try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                        psParticipations.setInt(1, id);
                        try (ResultSet participationRS = psParticipations.executeQuery()) {
                            while (participationRS.next()) {
                                lesson.getParticipations().add(participationRS.getInt("student_id"));
                            }
                        }
                    }

                    return Optional.of(lesson);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding lesson with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Lesson> findAll() {
        String findAllLessonSQL = "SELECT * FROM public.\"lessons\"";
        List<Lesson> lessonList = new ArrayList<>();

        try (PreparedStatement psFindAllLesson = connection.prepareStatement(findAllLessonSQL);
             ResultSet rs = psFindAllLesson.executeQuery()) {
            while (rs.next()) {
                Lesson lesson = new Lesson(
                        rs.getInt("id"),
                        rs.getInt("professor_id"),
                        rs.getInt("subject_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("class_id")
                );

                String participationsSQL = "SELECT student_id FROM lesson_participations WHERE lesson_id = ?";
                try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                    psParticipations.setInt(1, rs.getInt("id"));
                    try (ResultSet participationRS = psParticipations.executeQuery()) {
                        while (participationRS.next()) {
                            lesson.getParticipations().add(participationRS.getInt("student_id"));
                        }
                    }
                }

                lessonList.add(lesson);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all lessons", e);
        }
        return lessonList;
    }


    public List<Lesson> findAllToday() {
        LocalDate today = LocalDate.now();
        String findAllTodayLessonSQL = "SELECT * FROM public.\"lessons\" WHERE \"date\" = ?";

        List<Lesson> lessonList = new ArrayList<>();

        try (PreparedStatement psFindAllLesson = connection.prepareStatement(findAllTodayLessonSQL)) {
            psFindAllLesson.setDate(1, java.sql.Date.valueOf(today));

            try (ResultSet rs = psFindAllLesson.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = new Lesson(
                            rs.getInt("id"),
                            rs.getInt("professor_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("class_id")
                    );

                    String participationsSQL = "SELECT student_id FROM lesson_participations WHERE lesson_id = ?";
                    try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                        psParticipations.setInt(1, rs.getInt("id"));
                        try (ResultSet participationRS = psParticipations.executeQuery()) {
                            while (participationRS.next()) {
                                lesson.getParticipations().add(participationRS.getInt("student_id"));
                            }
                        }
                    }

                    lessonList.add(lesson);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding today's lessons", e);
        }

        return lessonList;
    }

}
