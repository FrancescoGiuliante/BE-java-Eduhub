package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseClassSubjectDAO {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public void assignSubjectToClass(int classId, int subjectId) {
        String insertSQL = "INSERT INTO public.\"class_subjects\" (class_id, subject_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setInt(1, classId);
            ps.setInt(2, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while assigning subject to class", e);
        }
    }

    public List<Integer> getSubjectsByClassId(int classId) {
        List<Integer> subjectIds = new ArrayList<>();
        String selectSQL = "SELECT subject_id FROM public.\"class_subjects\" WHERE class_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subjectIds.add(rs.getInt("subject_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving subjects for class ID: " + classId, e);
        }

        return subjectIds;
    }

    public void removeSubjectFromClass(int classId, int subjectId) {
        String deleteSQL = "DELETE FROM public.\"class_subjects\" WHERE class_id = ? AND subject_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
            ps.setInt(1, classId);
            ps.setInt(2, subjectId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No subject found for class ID " + classId + " and subject ID " + subjectId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing subject from class ID " + classId + " and subject ID " + subjectId, e);
        }
    }
}
