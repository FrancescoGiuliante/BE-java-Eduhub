package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectProfessorDAO {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public void assignProfessorToSubject(int subjectId, int professorId) {
        String insertSQL = "INSERT INTO public.\"subject_professors\" (subject_id, professor_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setInt(1, subjectId);
            ps.setInt(2, professorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while assigning professor to subject", e);
        }
    }

    public List<Integer> getProfessorsBySubjectId(int subjectId) {
        List<Integer> professorIds = new ArrayList<>();
        String selectSQL = "SELECT professor_id FROM public.\"subject_professors\" WHERE subject_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, subjectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    professorIds.add(rs.getInt("professor_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving professors for subject ID: " + subjectId, e);
        }

        return professorIds;
    }

    public void removeProfessorFromSubject(int subjectId, int professorId) {
        String deleteSQL = "DELETE FROM public.\"subject_professors\" WHERE subject_id = ? AND professor_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
            ps.setInt(1, subjectId);
            ps.setInt(2, professorId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No professor found for subject ID " + subjectId + " and professor ID " + professorId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while removing professor from subject ID " + subjectId + " and professor ID " + professorId, e);
        }
    }
}
