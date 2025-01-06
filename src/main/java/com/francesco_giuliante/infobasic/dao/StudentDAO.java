package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Student;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO implements GenericDAO<Student>{
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public Student save(Student student) {
        String insertStudentSQL = "INSERT INTO public.\"students\"(user_id, name, last_name, email)" + "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement psInsertStudent = connection.prepareStatement(insertStudentSQL, Statement.RETURN_GENERATED_KEYS);
            psInsertStudent.setInt(1, student.getUserId());
            psInsertStudent.setString(2, student.getName());
            psInsertStudent.setString(3, student.getLastName());
            psInsertStudent.setString(4, student.getEmail());

            psInsertStudent.executeUpdate();

            ResultSet generatedKeys = psInsertStudent.getGeneratedKeys();
            if (generatedKeys.next()) {
                int studentId = generatedKeys.getInt(1);
                student.setId(studentId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted student.");
            }

            return student;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteStudentByIdSQL = "DELETE FROM public.\"students\" WHERE id = ?";

        try {
            PreparedStatement psDeleteStudentById = connection.prepareStatement(deleteStudentByIdSQL);
            psDeleteStudentById.setInt(1, id);
            psDeleteStudentById.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Student update(Student student, int userID) {
        String updateStudentSQL = "UPDATE public.\"students\" SET name = ?, last_name = ?, email = ? WHERE user_id = ?";

        try (PreparedStatement psUpdateStudent = connection.prepareStatement(updateStudentSQL)) {
            psUpdateStudent.setString(1, student.getName());
            psUpdateStudent.setString(2, student.getLastName());
            psUpdateStudent.setString(3, student.getEmail());
            psUpdateStudent.setInt(4, userID);

            int rowsAffected = psUpdateStudent.executeUpdate();
            if (rowsAffected > 0) {
                String getUserIdSQL = "SELECT user_id FROM public.\"students\" WHERE user_id = ?";
                try (PreparedStatement psGetUserId = connection.prepareStatement(getUserIdSQL)) {
                    psGetUserId.setInt(1, userID);
                    try (ResultSet rs = psGetUserId.executeQuery()) {
                        if (rs.next()) {
                            student.setUserId(rs.getInt("user_id"));
                            return student;
                        } else {
                            throw new SQLException("User ID not found for user_id: " + userID);
                        }
                    }
                }
            } else {
                throw new SQLException("No student found with user_id: " + userID);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating student with user_id: " + userID, e);
        }
    }



    @Override
    public Optional<Student> findById(int id) {
        String findStudentByIdSQL = "SELECT * FROM public.\"students\" WHERE id = ?";

        try (PreparedStatement psFindStudent = connection.prepareStatement(findStudentByIdSQL)) {
            psFindStudent.setInt(1, id);

            try (ResultSet rs = psFindStudent.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("last_name"),
                            rs.getString("email")
                    );

                    String voteIDsSQL = "SELECT id FROM votes WHERE student_id = ?";
                    try (PreparedStatement psVoteIDs = connection.prepareStatement(voteIDsSQL)) {
                        psVoteIDs.setInt(1, id);
                        try (ResultSet voteRS = psVoteIDs.executeQuery()) {
                            while (voteRS.next()) {
                                student.getVoteIDs().add(voteRS.getInt("id"));
                            }
                        }
                    }

                    String classIDsSQL = "SELECT class_id FROM class_students WHERE student_id = ?";
                    try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                        psClassIDs.setInt(1, id);
                        try (ResultSet classRS = psClassIDs.executeQuery()) {
                            while (classRS.next()) {
                                student.getClassIDs().add(classRS.getInt("class_id"));
                            }
                        }
                    }

                    String participationsSQL = "SELECT lesson_id FROM lesson_participations WHERE student_id = ?";
                    try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                        psParticipations.setInt(1, id);
                        try (ResultSet participationRS = psParticipations.executeQuery()) {
                            while (participationRS.next()) {
                                student.getParticipations().add(participationRS.getInt("lesson_id"));
                            }
                        }
                    }

                    return Optional.of(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding student with ID: " + id, e);
        }

        return Optional.empty();
    }


    public Optional<Student> findByUserId(int userId) {
        String findStudentByUserIdSQL = "SELECT * FROM public.\"students\" WHERE user_id = ?";

        try (PreparedStatement psFindStudent = connection.prepareStatement(findStudentByUserIdSQL)) {
            psFindStudent.setInt(1, userId);

            try (ResultSet rs = psFindStudent.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("last_name"),
                            rs.getString("email")
                    );

                    String voteIDsSQL = "SELECT id FROM votes WHERE student_id = ?";
                    try (PreparedStatement psVoteIDs = connection.prepareStatement(voteIDsSQL)) {
                        psVoteIDs.setInt(1, rs.getInt("id"));
                        try (ResultSet voteRS = psVoteIDs.executeQuery()) {
                            while (voteRS.next()) {
                                student.getVoteIDs().add(voteRS.getInt("id"));
                            }
                        }
                    }

                    String classIDsSQL = "SELECT class_id FROM class_students WHERE student_id = ?";
                    try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                        psClassIDs.setInt(1, rs.getInt("id"));
                        try (ResultSet classRS = psClassIDs.executeQuery()) {
                            while (classRS.next()) {
                                student.getClassIDs().add(classRS.getInt("class_id"));
                            }
                        }
                    }

                    String participationsSQL = "SELECT lesson_id FROM lesson_participations WHERE student_id = ?";
                    try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                        psParticipations.setInt(1, rs.getInt("id"));
                        try (ResultSet participationRS = psParticipations.executeQuery()) {
                            while (participationRS.next()) {
                                student.getParticipations().add(participationRS.getInt("lesson_id"));
                            }
                        }
                    }

                    return Optional.of(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding student with user_id: " + userId, e);
        }

        return Optional.empty();
    }


    @Override
    public List<Student> findAll() {
        String findAllStudentSQL = "SELECT * FROM public.\"students\"";
        List<Student> studentList = new ArrayList<>();

        try (PreparedStatement psFindAllStudent = connection.prepareStatement(findAllStudentSQL);
             ResultSet rs = psFindAllStudent.executeQuery()) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                );

                String voteIDsSQL = "SELECT id FROM votes WHERE student_id = ?";
                try (PreparedStatement psVoteIDs = connection.prepareStatement(voteIDsSQL)) {
                    psVoteIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet voteRS = psVoteIDs.executeQuery()) {
                        while (voteRS.next()) {
                            student.getVoteIDs().add(voteRS.getInt("id"));
                        }
                    }
                }

                String classIDsSQL = "SELECT class_id FROM class_students WHERE student_id = ?";
                try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                    psClassIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet classRS = psClassIDs.executeQuery()) {
                        while (classRS.next()) {
                            student.getClassIDs().add(classRS.getInt("class_id"));
                        }
                    }
                }

                String participationsSQL = "SELECT lesson_id FROM lesson_participations WHERE student_id = ?";
                try (PreparedStatement psParticipations = connection.prepareStatement(participationsSQL)) {
                    psParticipations.setInt(1, rs.getInt("id"));
                    try (ResultSet participationRS = psParticipations.executeQuery()) {
                        while (participationRS.next()) {
                            student.getParticipations().add(participationRS.getInt("lesson_id"));
                        }
                    }
                }

                studentList.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all students", e);
        }
        return studentList;
    }

}
