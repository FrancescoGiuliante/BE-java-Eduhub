package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseClassStudentDAO {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public void enrollStudentInClass(int classId, int studentId) {
        String insertSQL = "INSERT INTO public.\"class_students\" (class_id, student_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setInt(1, classId);
            ps.setInt(2, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while enrolling student in class", e);
        }
    }

    public List<Integer> getStudentsByClassId(int classId) {
        List<Integer> studentIds = new ArrayList<>();
        String selectSQL = "SELECT student_id FROM public.\"class_students\" WHERE class_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    studentIds.add(rs.getInt("student_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving students for class ID: " + classId, e);
        }

        return studentIds;
    }

    public void removeStudentFromClass(int classId, int studentId) {
        String deleteSQL = "DELETE FROM public.\"class_students\" WHERE class_id = ? AND student_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
            ps.setInt(1, classId);
            ps.setInt(2, studentId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No student found for class ID " + classId + " and student ID " + studentId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing student from class ID " + classId + " and student ID " + studentId, e);
        }
    }
}
