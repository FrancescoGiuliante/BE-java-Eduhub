package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.WeekProgram;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeekProgramDAO implements GenericDAO<WeekProgram> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public WeekProgram save(WeekProgram weekProgram) {
        String insertWeekProgramSQL = "INSERT INTO public.\"week_program\"(start_date, end_date, class_id) VALUES (?, ?, ?)";

        try (PreparedStatement psInsertWeekProgram = connection.prepareStatement(insertWeekProgramSQL, Statement.RETURN_GENERATED_KEYS)) {
            psInsertWeekProgram.setDate(1, Date.valueOf(weekProgram.getStartDate()));
            psInsertWeekProgram.setDate(2, Date.valueOf(weekProgram.getEndDate()));
            psInsertWeekProgram.setInt(3, weekProgram.getClassID());

            psInsertWeekProgram.executeUpdate();

            ResultSet generatedKeys = psInsertWeekProgram.getGeneratedKeys();
            if (generatedKeys.next()) {
                int weekProgramId = generatedKeys.getInt(1);
                weekProgram.setId(weekProgramId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted week program.");
            }

            return weekProgram;
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving week program", e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteWeekProgramByIdSQL = "DELETE FROM public.\"week_program\" WHERE id = ?";

        try (PreparedStatement psDeleteWeekProgram = connection.prepareStatement(deleteWeekProgramByIdSQL)) {
            psDeleteWeekProgram.setInt(1, id);
            psDeleteWeekProgram.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting week program with ID: " + id, e);
        }
    }

    @Override
    public WeekProgram update(WeekProgram weekProgram, int id) {
        String updateWeekProgramSQL = "UPDATE public.\"week_program\" SET start_date = ?, end_date = ?, class_id = ? WHERE id = ?";

        try (PreparedStatement psUpdateWeekProgram = connection.prepareStatement(updateWeekProgramSQL)) {
            psUpdateWeekProgram.setDate(1, Date.valueOf(weekProgram.getStartDate()));
            psUpdateWeekProgram.setDate(2, Date.valueOf(weekProgram.getEndDate()));
            psUpdateWeekProgram.setInt(3, weekProgram.getClassID());
            psUpdateWeekProgram.setInt(4, id);

            int rowsAffected = psUpdateWeekProgram.executeUpdate();
            if (rowsAffected > 0) {
                weekProgram.setId(id);
                return weekProgram;
            } else {
                throw new SQLException("No week program found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating week program with ID: " + id, e);
        }
    }

    public Optional<WeekProgram> findById(int id) {
        String findWeekProgramByIdSQL = "SELECT * FROM public.\"week_program\" WHERE id = ?";

        try (PreparedStatement psFindWeekProgram = connection.prepareStatement(findWeekProgramByIdSQL)) {
            psFindWeekProgram.setInt(1, id);

            try (ResultSet rs = psFindWeekProgram.executeQuery()) {
                if (rs.next()) {
                    WeekProgram weekProgram = new WeekProgram(
                            rs.getInt("id"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getInt("class_id")
                    );

                    String findLessonsSQL = "SELECT id FROM public.\"lessons\" WHERE class_id = ? AND date BETWEEN ? AND ?";
                    try (PreparedStatement psFindLessons = connection.prepareStatement(findLessonsSQL)) {
                        psFindLessons.setInt(1, weekProgram.getClassID());
                        psFindLessons.setDate(2, java.sql.Date.valueOf(weekProgram.getStartDate()));
                        psFindLessons.setDate(3, java.sql.Date.valueOf(weekProgram.getEndDate()));
                        try (ResultSet lessonsRS = psFindLessons.executeQuery()) {
                            while (lessonsRS.next()) {
                                weekProgram.getLessonIDs().add(lessonsRS.getInt("id"));
                            }
                        }
                    }

                    return Optional.of(weekProgram);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding week program with ID: " + id, e);
        }

        return Optional.empty();
    }


    @Override
    public List<WeekProgram> findAll() {
        String findAllWeekProgramsSQL = "SELECT * FROM public.\"week_program\"";
        List<WeekProgram> weekProgramList = new ArrayList<>();

        try (PreparedStatement psFindAllWeekPrograms = connection.prepareStatement(findAllWeekProgramsSQL);
             ResultSet rs = psFindAllWeekPrograms.executeQuery()) {
            while (rs.next()) {
                WeekProgram weekProgram = new WeekProgram(
                        rs.getInt("id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getInt("class_id")
                );

                String findLessonsSQL = "SELECT id FROM public.\"lessons\" WHERE class_id = ? AND date BETWEEN ? AND ?";
                try (PreparedStatement psFindLessons = connection.prepareStatement(findLessonsSQL)) {
                    psFindLessons.setInt(1, weekProgram.getClassID());
                    psFindLessons.setDate(2, java.sql.Date.valueOf(weekProgram.getStartDate()));
                    psFindLessons.setDate(3, java.sql.Date.valueOf(weekProgram.getEndDate()));
                    try (ResultSet lessonsRS = psFindLessons.executeQuery()) {
                        while (lessonsRS.next()) {
                            weekProgram.getLessonIDs().add(lessonsRS.getInt("id"));
                        }
                    }
                }

                weekProgramList.add(weekProgram);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all week programs", e);
        }

        return weekProgramList;
    }

}
