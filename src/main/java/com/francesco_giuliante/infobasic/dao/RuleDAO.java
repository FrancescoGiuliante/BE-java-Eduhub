package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Rule;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuleDAO {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public Rule save(Rule rule) {
        String sql = "INSERT INTO public.\"rules\" (professor_id, value_right_answer, value_wrong_answer, duration) " +
                "VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rule.getProfessorID());
            ps.setDouble(2, rule.getValueRightAnswer());
            ps.setDouble(3, rule.getValueWrongAnswer());
            ps.setInt(4, rule.getDuration());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rule.setId(rs.getInt("id"));
            }
            return rule;
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving rule", e);
        }
    }

    public Optional<Rule> findById(int id) {
        String sql = "SELECT * FROM public.\"rules\" WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setProfessorID(rs.getInt("professor_id"));
                    rule.setValueRightAnswer(rs.getDouble("value_right_answer"));
                    rule.setValueWrongAnswer(rs.getDouble("value_wrong_answer"));
                    rule.setDuration(rs.getInt("duration"));

                    String quizzesSQL = "SELECT id FROM public.\"quizzes\" WHERE rule_id = ?";
                    try (PreparedStatement psQuizzes = connection.prepareStatement(quizzesSQL)) {
                        psQuizzes.setInt(1, id);
                        try (ResultSet quizzesRS = psQuizzes.executeQuery()) {
                            while (quizzesRS.next()) {
                                rule.getQuizIDs().add(quizzesRS.getInt("id"));
                            }
                        }
                    }

                    return Optional.of(rule);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding rule with ID: " + id, e);
        }
        return Optional.empty();
    }


    public Rule update(Rule rule, int id) {
        String sql = "UPDATE public.\"rules\" SET professor_id = ?, value_right_answer = ?, " +
                "value_wrong_answer = ?, duration = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rule.getProfessorID());
            ps.setDouble(2, rule.getValueRightAnswer());
            ps.setDouble(3, rule.getValueWrongAnswer());
            ps.setInt(4, rule.getDuration());
            ps.setInt(5, id);  // Pass ID of the rule to update
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return rule;
            } else {
                throw new SQLException("No rule found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating rule with ID: " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM public.\"rules\" WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting rule with ID: " + id, e);
        }
    }

    public List<Rule> findAll() {
        String sql = "SELECT * FROM public.\"rules\"";
        List<Rule> ruleList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Rule rule = new Rule();
                rule.setId(rs.getInt("id"));
                rule.setProfessorID(rs.getInt("professor_id"));
                rule.setValueRightAnswer(rs.getDouble("value_right_answer"));
                rule.setValueWrongAnswer(rs.getDouble("value_wrong_answer"));
                rule.setDuration(rs.getInt("duration"));

                String quizzesSQL = "SELECT id FROM public.\"quizzes\" WHERE rule_id = ?";
                try (PreparedStatement psQuizzes = connection.prepareStatement(quizzesSQL)) {
                    psQuizzes.setInt(1, rs.getInt("id"));
                    try (ResultSet quizzesRS = psQuizzes.executeQuery()) {
                        while (quizzesRS.next()) {
                            rule.getQuizIDs().add(quizzesRS.getInt("id"));
                        }
                    }
                }
                ruleList.add(rule);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all rules", e);
        }
        return ruleList;
    }

    public List<Rule> findAllByProfessorID(int professorId) {
        String sql = "SELECT * FROM public.\"rules\" WHERE professor_id = ?";
        List<Rule> ruleList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, professorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setProfessorID(rs.getInt("professor_id"));
                    rule.setValueRightAnswer(rs.getDouble("value_right_answer"));
                    rule.setValueWrongAnswer(rs.getDouble("value_wrong_answer"));
                    rule.setDuration(rs.getInt("duration"));

                    String quizzesSQL = "SELECT id FROM public.\"quizzes\" WHERE rule_id = ?";
                    try (PreparedStatement psQuizzes = connection.prepareStatement(quizzesSQL)) {
                        psQuizzes.setInt(1, rs.getInt("id"));
                        try (ResultSet quizzesRS = psQuizzes.executeQuery()) {
                            while (quizzesRS.next()) {
                                rule.getQuizIDs().add(quizzesRS.getInt("id"));
                            }
                        }
                    }
                    ruleList.add(rule);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding rules by professor ID: " + professorId, e);
        }
        return ruleList;
    }


}
