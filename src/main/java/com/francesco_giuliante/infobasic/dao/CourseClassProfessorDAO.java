package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseClassProfessorDAO {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public void assignProfessorToClass(int classId, int professorId) {
        String insertSQL = "INSERT INTO public.\"class_professors\" (class_id, professor_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setInt(1, classId);
            ps.setInt(2, professorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while assigning professor to class", e);
        }
    }

    public List<Integer> getProfessorsByClassId(int classId) {
        List<Integer> professorIds = new ArrayList<>();
        String selectSQL = "SELECT professor_id FROM public.\"class_professors\" WHERE class_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    professorIds.add(rs.getInt("professor_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving professors for class ID: " + classId, e);
        }

        return professorIds;
    }

    public void removeProfessorFromClass(int classId, int professorId) {
        String deleteSQL = "DELETE FROM public.\"class_professors\" WHERE class_id = ? AND professor_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
            ps.setInt(1, classId);
            ps.setInt(2, professorId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No professor found for class ID " + classId + " and professor ID " + professorId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing professor from class ID " + classId + " and professor ID " + professorId, e);
        }
    }
}
