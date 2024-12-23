package com.francesco_giuliante.infobasic.dao;


import com.francesco_giuliante.infobasic.model.CourseClass;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseClassDAO implements GenericDAO<CourseClass> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public CourseClass save(CourseClass courseClass) {
        String insertCourseClassSQL = "INSERT INTO public.\"classes\"(path, name, syllabus, course_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement psInsertCourseClass = connection.prepareStatement(insertCourseClassSQL, Statement.RETURN_GENERATED_KEYS);
            psInsertCourseClass.setString(1, courseClass.getPath());
            psInsertCourseClass.setString(2, courseClass.getName());
            psInsertCourseClass.setString(3, courseClass.getSyllabus());
            psInsertCourseClass.setInt(4, courseClass.getCourseID());

            psInsertCourseClass.executeUpdate();

            ResultSet generatedKeys = psInsertCourseClass.getGeneratedKeys();
            if (generatedKeys.next()) {
                int courseClassId = generatedKeys.getInt(1);
                courseClass.setId(courseClassId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted courseClass.");
            }

            return courseClass;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteCourseClassByIdSQL = "DELETE FROM public.\"classes\" WHERE id = ?";

        try {
            PreparedStatement psDeleteCourseClassById = connection.prepareStatement(deleteCourseClassByIdSQL);
            psDeleteCourseClassById.setInt(1, id);
            psDeleteCourseClassById.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CourseClass update(CourseClass courseClass, int id) {
        String updateCourseClassSQL = "UPDATE public.\"classes\" SET path = ?, name = ?, syllabus = ?, course_id = ? WHERE id = ?";

        try (PreparedStatement psUpdateCourseClass = connection.prepareStatement(updateCourseClassSQL)) {
            psUpdateCourseClass.setString(1, courseClass.getPath());
            psUpdateCourseClass.setString(2, courseClass.getName());
            psUpdateCourseClass.setString(3, courseClass.getSyllabus());
            psUpdateCourseClass.setInt(4, courseClass.getCourseID());
            psUpdateCourseClass.setInt(5, id);

            int rowsAffected = psUpdateCourseClass.executeUpdate();
            if (rowsAffected > 0) {
                courseClass.setId(id);
                return courseClass;
            } else {
                throw new SQLException("No courseClass found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating courseClass with ID: " + id, e);
        }
    }

    @Override
    public Optional<CourseClass> findById(int id) {
        String findCourseClassByIdSQL = "SELECT * FROM public.\"classes\" WHERE id = ?";

        try (PreparedStatement psFindCourseClass = connection.prepareStatement(findCourseClassByIdSQL)) {
            psFindCourseClass.setInt(1, id);

            try (ResultSet rs = psFindCourseClass.executeQuery()) {
                if (rs.next()) {
                    CourseClass courseClass = new CourseClass(
                            rs.getInt("id"),
                            rs.getString("path"),
                            rs.getString("name"),
                            rs.getString("syllabus"),
                            rs.getInt("course_id")
                    );

                    String studentIDsSQL = "SELECT student_id FROM class_students WHERE class_id = ?";
                    try (PreparedStatement psStudentIDs = connection.prepareStatement(studentIDsSQL)) {
                        psStudentIDs.setInt(1, id);
                        try (ResultSet studentRS = psStudentIDs.executeQuery()) {
                            while (studentRS.next()) {
                                courseClass.getStudentIDs().add(studentRS.getInt("student_id"));
                            }
                        }
                    }

                    String professorIDsSQL = "SELECT professor_id FROM class_professors WHERE class_id = ?";
                    try (PreparedStatement psProfessorIDs = connection.prepareStatement(professorIDsSQL)) {
                        psProfessorIDs.setInt(1, id);
                        try (ResultSet professorRS = psProfessorIDs.executeQuery()) {
                            while (professorRS.next()) {
                                courseClass.getProfessorIDs().add(professorRS.getInt("professor_id"));
                            }
                        }
                    }

                    String subjectIDsSQL = "SELECT subject_id FROM class_subjects WHERE class_id = ?";
                    try (PreparedStatement psSubjectIDs = connection.prepareStatement(subjectIDsSQL)) {
                        psSubjectIDs.setInt(1, id);
                        try (ResultSet subjectRS = psSubjectIDs.executeQuery()) {
                            while (subjectRS.next()) {
                                courseClass.getSubjectIDs().add(subjectRS.getInt("subject_id"));
                            }
                        }
                    }

                    String weekProgramIDsSQL = "SELECT id FROM week_program WHERE class_id = ?";
                    try (PreparedStatement psWeekProgramIDs = connection.prepareStatement(weekProgramIDsSQL)) {
                        psWeekProgramIDs.setInt(1, rs.getInt("id"));
                        try (ResultSet weekProgramRS = psWeekProgramIDs.executeQuery()) {
                            while (weekProgramRS.next()) {
                                courseClass.getWeekProgramIDs().add(weekProgramRS.getInt("id"));
                            }
                        }
                    }

                    return Optional.of(courseClass);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding courseClass with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<CourseClass> findAll() {
        String findAllCourseClassSQL = "SELECT * FROM public.\"classes\"";
        List<CourseClass> courseClassList = new ArrayList<>();

        try (PreparedStatement psFindAllCourseClass = connection.prepareStatement(findAllCourseClassSQL);
             ResultSet rs = psFindAllCourseClass.executeQuery()) {
            while (rs.next()) {
                CourseClass courseClass = new CourseClass(
                        rs.getInt("id"),
                        rs.getString("path"),
                        rs.getString("name"),
                        rs.getString("syllabus"),
                        rs.getInt("course_id")
                );

                String studentIDsSQL = "SELECT student_id FROM class_students WHERE class_id = ?";
                try (PreparedStatement psStudentIDs = connection.prepareStatement(studentIDsSQL)) {
                    psStudentIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet studentRS = psStudentIDs.executeQuery()) {
                        while (studentRS.next()) {
                            courseClass.getStudentIDs().add(studentRS.getInt("student_id"));
                        }
                    }
                }

                String professorIDsSQL = "SELECT professor_id FROM class_professors WHERE class_id = ?";
                try (PreparedStatement psProfessorIDs = connection.prepareStatement(professorIDsSQL)) {
                    psProfessorIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet professorRS = psProfessorIDs.executeQuery()) {
                        while (professorRS.next()) {
                            courseClass.getProfessorIDs().add(professorRS.getInt("professor_id"));
                        }
                    }
                }

                String subjectIDsSQL = "SELECT subject_id FROM class_subjects WHERE class_id = ?";
                try (PreparedStatement psSubjectIDs = connection.prepareStatement(subjectIDsSQL)) {
                    psSubjectIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet subjectRS = psSubjectIDs.executeQuery()) {
                        while (subjectRS.next()) {
                            courseClass.getSubjectIDs().add(subjectRS.getInt("subject_id"));
                        }
                    }
                }

                String weekProgramIDsSQL = "SELECT id FROM week_program WHERE class_id = ?";
                try (PreparedStatement psWeekProgramIDs = connection.prepareStatement(weekProgramIDsSQL)) {
                    psWeekProgramIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet weekProgramRS = psWeekProgramIDs.executeQuery()) {
                        while (weekProgramRS.next()) {
                            courseClass.getWeekProgramIDs().add(weekProgramRS.getInt("id"));
                        }
                    }
                }

                courseClassList.add(courseClass);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all courseClasses", e);
        }
        return courseClassList;
    }
}

