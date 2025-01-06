package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Course;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAO implements GenericDAO<Course> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public Course save(Course course) {
        String insertCourseSQL = "INSERT INTO public.\"courses\"(description, path) VALUES (?, ?)";

        try {
            PreparedStatement psInsertCourse = connection.prepareStatement(insertCourseSQL, Statement.RETURN_GENERATED_KEYS);
            psInsertCourse.setString(1, course.getDescription());
            psInsertCourse.setString(2, course.getPath());

            psInsertCourse.executeUpdate();

            ResultSet generatedKeys = psInsertCourse.getGeneratedKeys();
            if (generatedKeys.next()) {
                int courseId = generatedKeys.getInt(1);
                course.setId(courseId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted course.");
            }

            return course;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteCourseByIdSQL = "DELETE FROM public.\"courses\" WHERE id = ?";

        try {
            PreparedStatement psDeleteCourseById = connection.prepareStatement(deleteCourseByIdSQL);
            psDeleteCourseById.setInt(1, id);
            psDeleteCourseById.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course update(Course course, int id) {
        String updateCourseSQL = "UPDATE public.\"courses\" SET description = ?, path = ? WHERE id = ?";

        try (PreparedStatement psUpdateCourse = connection.prepareStatement(updateCourseSQL)) {
            psUpdateCourse.setString(1, course.getDescription());
            psUpdateCourse.setString(2, course.getPath());
            psUpdateCourse.setInt(3, id);

            int rowsAffected = psUpdateCourse.executeUpdate();
            if (rowsAffected > 0) {
                course.setId(id);
                return course;
            } else {
                throw new SQLException("No course found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating course with ID: " + id, e);
        }
    }

    @Override
    public Optional<Course> findById(int id) {
        String findCourseByIdSQL = "SELECT * FROM public.\"courses\" WHERE id = ?";

        try (PreparedStatement psFindCourse = connection.prepareStatement(findCourseByIdSQL)) {
            psFindCourse.setInt(1, id);

            try (ResultSet rs = psFindCourse.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course(
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getString("path")
                    );


                    String classIDsSQL = "SELECT id FROM classes WHERE course_id = ?";
                    try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                        psClassIDs.setInt(1, id);
                        try (ResultSet classRS = psClassIDs.executeQuery()) {
                            while (classRS.next()) {
                                int classId = classRS.getInt("id");

                                String professorIDsSQL = "SELECT professor_id FROM class_professors WHERE class_id = ?";
                                try (PreparedStatement psProfessorIDs = connection.prepareStatement(professorIDsSQL)) {
                                    psProfessorIDs.setInt(1, classId);
                                    try (ResultSet professorRS = psProfessorIDs.executeQuery()) {
                                        while (professorRS.next()) {
                                            course.getProfessorIDs().add(professorRS.getInt("professor_id"));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                        psClassIDs.setInt(1, id);
                        try (ResultSet classRS = psClassIDs.executeQuery()) {
                            while (classRS.next()) {
                                course.getClassIDs().add(classRS.getInt("id"));
                            }
                        }
                    }

                    return Optional.of(course);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding course with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Course> findAll() {
        String findAllCourseSQL = "SELECT * FROM public.\"courses\"";
        List<Course> courseList = new ArrayList<>();

        try (PreparedStatement psFindAllCourse = connection.prepareStatement(findAllCourseSQL);
             ResultSet rs = psFindAllCourse.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("path")
                );

                String classIDsSQL = "SELECT id FROM classes WHERE course_id = ?";
                try (PreparedStatement psClassIDs = connection.prepareStatement(classIDsSQL)) {
                    psClassIDs.setInt(1, rs.getInt("id"));
                    try (ResultSet classRS = psClassIDs.executeQuery()) {
                        while (classRS.next()) {
                            int classId = classRS.getInt("id");
                            course.getClassIDs().add(classId);

                            String professorIDsSQL = "SELECT professor_id FROM class_professors WHERE class_id = ?";
                            try (PreparedStatement psProfessorIDs = connection.prepareStatement(professorIDsSQL)) {
                                psProfessorIDs.setInt(1, classId);
                                try (ResultSet professorRS = psProfessorIDs.executeQuery()) {
                                    while (professorRS.next()) {
                                        course.getProfessorIDs().add(professorRS.getInt("professor_id"));
                                    }
                                }
                            }
                        }
                    }
                }

                courseList.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all courses", e);
        }
        return courseList;
    }

}

