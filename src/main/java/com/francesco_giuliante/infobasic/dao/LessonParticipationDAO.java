package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonParticipationDAO {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public void registerAttendance(int lessonId, int studentId) {
        String insertSQL = "INSERT INTO public.\"lesson_participations\" (lesson_id, student_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setInt(1, lessonId);
            ps.setInt(2, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while registering attendance", e);
        }
    }

    public List<Integer> getStudentsByLessonId(int lessonId) {
        List<Integer> studentIds = new ArrayList<>();
        String selectSQL = "SELECT student_id FROM public.\"lesson_participations\" WHERE lesson_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, lessonId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    studentIds.add(rs.getInt("student_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving students for lesson ID: " + lessonId, e);
        }

        return studentIds;
    }

    public void deleteAttendance(int lessonId, int studentId) {
        String deleteSQL = "DELETE FROM public.\"lesson_participations\" WHERE lesson_id = ? AND student_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
            ps.setInt(1, lessonId);
            ps.setInt(2, studentId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No attendance found for lesson ID " + lessonId + " and student ID " + studentId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting attendance for lesson ID " + lessonId + " and student ID " + studentId, e);
        }
    }
}
