package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Professor;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDAO implements GenericDAO<Professor> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public Professor save(Professor professor) {
        String insertProfessorSQL = "INSERT INTO public.\"professors\"(user_id, name, last_name, email, biography) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement psInsertProfessor = connection.prepareStatement(insertProfessorSQL, Statement.RETURN_GENERATED_KEYS);
            psInsertProfessor.setInt(1, professor.getUserId());
            psInsertProfessor.setString(2, professor.getName());
            psInsertProfessor.setString(3, professor.getLastName());
            psInsertProfessor.setString(4, professor.getEmail());
            psInsertProfessor.setString(5, professor.getBiography());

            psInsertProfessor.executeUpdate();

            ResultSet generatedKeys = psInsertProfessor.getGeneratedKeys();
            if (generatedKeys.next()) {
                int professorId = generatedKeys.getInt(1);
                professor.setId(professorId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted professor.");
            }

            return professor;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteProfessorByIdSQL = "DELETE FROM public.\"professors\" WHERE id = ?";

        try {
            PreparedStatement psDeleteProfessorById = connection.prepareStatement(deleteProfessorByIdSQL);
            psDeleteProfessorById.setInt(1, id);
            psDeleteProfessorById.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Professor update(Professor professor, int id) {
        String updateProfessorSQL = "UPDATE public.\"professors\" SET name = ?, last_name = ?, email = ?, biography = ? WHERE id = ?";

        try (PreparedStatement psUpdateProfessor = connection.prepareStatement(updateProfessorSQL)) {
            psUpdateProfessor.setString(1, professor.getName());
            psUpdateProfessor.setString(2, professor.getLastName());
            psUpdateProfessor.setString(3, professor.getEmail());
            psUpdateProfessor.setString(4, professor.getBiography());
            psUpdateProfessor.setInt(5, id);

            int rowsAffected = psUpdateProfessor.executeUpdate();
            if (rowsAffected > 0) {
                String getUserIdSQL = "SELECT user_id FROM public.\"professors\" WHERE id = ?";
                try (PreparedStatement psGetUserId = connection.prepareStatement(getUserIdSQL)) {
                    psGetUserId.setInt(1, id);
                    try (ResultSet rs = psGetUserId.executeQuery()) {
                        if (rs.next()) {
                            professor.setId(id);
                            professor.setUserId(rs.getInt("user_id"));  // Set user_id
                            return professor;
                        } else {
                            throw new SQLException("User ID not found for professor ID: " + id);
                        }
                    }
                }
            } else {
                throw new SQLException("No professor found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating professor with ID: " + id, e);
        }
    }

    @Override
    public Optional<Professor> findById(int id) {
        String findProfessorByIdSQL = "SELECT * FROM public.\"professors\" WHERE id = ?";

        try (PreparedStatement psFindProfessor = connection.prepareStatement(findProfessorByIdSQL)) {
            psFindProfessor.setInt(1, id);

            try (ResultSet rs = psFindProfessor.executeQuery()) {
                if (rs.next()) {
                    Professor professor = new Professor(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("biography")
                    );

                    String classIDsSQL = "SELECT class_id FROM class_professors WHERE professor_id = ?";
                    try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                        psClassIDs.setInt(1, rs.getInt("id"));
                        try (ResultSet classRS = psClassIDs.executeQuery()) {
                            while (classRS.next()) {
                                professor.getClassIDs().add(classRS.getInt("class_id"));
                            }
                        }
                    }

                    String participationsSQL = "SELECT id FROM lessons WHERE professor_id = ?";
                    try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                        psParticipations.setInt(1, rs.getInt("id"));
                        try (ResultSet participationRS = psParticipations.executeQuery()) {
                            while (participationRS.next()) {
                                professor.getParticipations().add(participationRS.getInt("id"));
                            }
                        }
                    }

                    String questionIDsSQL = "SELECT id FROM questions WHERE professor_id = ?";
                    try (PreparedStatement psQuestionIDs = connection.prepareStatement(questionIDsSQL)) {
                        psQuestionIDs.setInt(1, id);
                        try (ResultSet questionRS = psQuestionIDs.executeQuery()) {
                            while (questionRS.next()) {
                                professor.getQuestionIDs().add(questionRS.getInt("question_id"));
                            }
                        }
                    }

                    String quizIDsSQL = "SELECT id FROM quizzes WHERE professor_id = ?";
                    try (PreparedStatement psQuizIDs = connection.prepareStatement(quizIDsSQL)) {
                        psQuizIDs.setInt(1, id);
                        try (ResultSet quizRS = psQuizIDs.executeQuery()) {
                            while (quizRS.next()) {
                                professor.getQuizIDs().add(quizRS.getInt("quiz_id"));
                            }
                        }
                    }

                    return Optional.of(professor);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding professor with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Professor> findAll() {
        String findAllProfessorSQL = "SELECT * FROM public.\"professors\"";
        List<Professor> professorList = new ArrayList<>();

        try (PreparedStatement psFindAllProfessor = connection.prepareStatement(findAllProfessorSQL);
             ResultSet rs = psFindAllProfessor.executeQuery()) {
            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("biography")
                );

                String classIDsSQL = "SELECT class_id FROM class_professors WHERE professor_id = ?";
                try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                    psClassIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet classRS = psClassIDs.executeQuery()) {
                        while (classRS.next()) {
                            professor.getClassIDs().add(classRS.getInt("class_id"));
                        }
                    }
                }

                String participationsSQL = "SELECT id FROM lessons WHERE professor_id = ?";
                try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                    psParticipations.setInt(1, rs.getInt("id"));
                    try (ResultSet participationRS = psParticipations.executeQuery()) {
                        while (participationRS.next()) {
                            professor.getParticipations().add(participationRS.getInt("id"));
                        }
                    }
                }

                String questionIDsSQL = "SELECT id FROM questions WHERE professor_id = ?";
                try (PreparedStatement psQuestionIDs = connection.prepareStatement(questionIDsSQL)) {
                    psQuestionIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet questionRS = psQuestionIDs.executeQuery()) {
                        while (questionRS.next()) {
                            professor.getQuestionIDs().add(questionRS.getInt("question_id"));
                        }
                    }
                }

                String quizIDsSQL = "SELECT id FROM quizzes WHERE professor_id = ?";
                try (PreparedStatement psQuizIDs = connection.prepareStatement(quizIDsSQL)) {
                    psQuizIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet quizRS = psQuizIDs.executeQuery()) {
                        while (quizRS.next()) {
                            professor.getQuizIDs().add(quizRS.getInt("quiz_id"));
                        }
                    }
                }

                professorList.add(professor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all professors", e);
        }
        return professorList;
    }
}
