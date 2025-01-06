package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Subject;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectDAO implements GenericDAO<Subject> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public Subject save(Subject subject) {
        String insertSubjectSQL = "INSERT INTO public.\"subjects\"(name, description) VALUES (?, ?)";

        try (PreparedStatement psInsertSubject = connection.prepareStatement(insertSubjectSQL, Statement.RETURN_GENERATED_KEYS)) {
            psInsertSubject.setString(1, subject.getName());
            psInsertSubject.setString(2, subject.getDescription());

            psInsertSubject.executeUpdate();

            ResultSet generatedKeys = psInsertSubject.getGeneratedKeys();
            if (generatedKeys.next()) {
                int subjectId = generatedKeys.getInt(1);
                subject.setId(subjectId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted subject.");
            }

            return subject;
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving subject", e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteSubjectByIdSQL = "DELETE FROM public.\"subjects\" WHERE id = ?";

        try (PreparedStatement psDeleteSubject = connection.prepareStatement(deleteSubjectByIdSQL)) {
            psDeleteSubject.setInt(1, id);
            psDeleteSubject.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting subject with ID: " + id, e);
        }
    }

    @Override
    public Subject update(Subject subject, int id) {
        String updateSubjectSQL = "UPDATE public.\"subjects\" SET name = ?, description = ? WHERE id = ?";

        try (PreparedStatement psUpdateSubject = connection.prepareStatement(updateSubjectSQL)) {
            psUpdateSubject.setString(1, subject.getName());
            psUpdateSubject.setString(2, subject.getDescription());
            psUpdateSubject.setInt(3, id);

            int rowsAffected = psUpdateSubject.executeUpdate();
            if (rowsAffected > 0) {
                subject.setId(id);
                return subject;
            } else {
                throw new SQLException("No subject found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating subject with ID: " + id, e);
        }
    }

    @Override
    public Optional<Subject> findById(int id) {
        String findSubjectByIdSQL = "SELECT * FROM public.\"subjects\" WHERE id = ?";

        try (PreparedStatement psFindSubject = connection.prepareStatement(findSubjectByIdSQL)) {
            psFindSubject.setInt(1, id);

            try (ResultSet rs = psFindSubject.executeQuery()) {
                if (rs.next()) {
                    Subject subject = new Subject(rs.getInt("id"), rs.getString("name"), rs.getString("description"));

                    String professorIDsSQL = "SELECT professor_id FROM subject_professors WHERE subject_id = ?";
                    try (PreparedStatement psProfessorIDs = connection.prepareStatement(professorIDsSQL)) {
                        psProfessorIDs.setInt(1, id);
                        try (ResultSet professorRS = psProfessorIDs.executeQuery()) {
                            while (professorRS.next()) {
                                subject.getProfessorIDs().add(professorRS.getInt("professor_id"));
                            }
                        }
                    }

                    String quizIDsSQL = "SELECT id FROM quizzes WHERE subject_id = ?";
                    try (PreparedStatement psQuizIDs = connection.prepareStatement(quizIDsSQL)) {
                        psQuizIDs.setInt(1, id);
                        try (ResultSet quizRS = psQuizIDs.executeQuery()) {
                            while (quizRS.next()) {
                                subject.getQuizIDs().add(quizRS.getInt("id"));
                            }
                        }
                    }

                    return Optional.of(subject);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding subject with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Subject> findAll() {
        String findAllSubjectSQL = "SELECT * FROM public.\"subjects\"";
        List<Subject> subjectList = new ArrayList<>();

        try (PreparedStatement psFindAllSubject = connection.prepareStatement(findAllSubjectSQL);
             ResultSet rs = psFindAllSubject.executeQuery()) {
            while (rs.next()) {
                Subject subject = new Subject(rs.getInt("id"), rs.getString("name"), rs.getString("description"));

                String professorIDsSQL = "SELECT professor_id FROM subject_professors WHERE subject_id = ?";
                try (PreparedStatement psProfessorIDs = connection.prepareStatement(professorIDsSQL)) {
                    psProfessorIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet professorRS = psProfessorIDs.executeQuery()) {
                        while (professorRS.next()) {
                            subject.getProfessorIDs().add(professorRS.getInt("professor_id"));
                        }
                    }
                }

                String quizIDsSQL = "SELECT id FROM quizzes WHERE subject_id = ?";
                try (PreparedStatement psQuizIDs = connection.prepareStatement(quizIDsSQL)) {
                    psQuizIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet quizRS = psQuizIDs.executeQuery()) {
                        while (quizRS.next()) {
                            subject.getQuizIDs().add(quizRS.getInt("id"));
                        }
                    }
                }

                subjectList.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all subjects", e);
        }
        return subjectList;
    }
}
