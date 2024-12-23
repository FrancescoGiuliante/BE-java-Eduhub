package com.francesco_giuliante.infobasic.dao;

import com.francesco_giuliante.infobasic.model.Vote;
import com.francesco_giuliante.infobasic.utility.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VoteDAO implements GenericDAO<Vote> {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public Vote save(Vote vote) {
        String insertVoteSQL = "INSERT INTO public.\"votes\"(quiz_id, student_id, date, result) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psInsertVote = connection.prepareStatement(insertVoteSQL, Statement.RETURN_GENERATED_KEYS)) {
            psInsertVote.setInt(1, vote.getQuizID());
            psInsertVote.setInt(2, vote.getStudentID());
            psInsertVote.setDate(3, Date.valueOf(vote.getDate()));
            psInsertVote.setDouble(4, vote.getResult());

            psInsertVote.executeUpdate();

            ResultSet generatedKeys = psInsertVote.getGeneratedKeys();
            if (generatedKeys.next()) {
                int voteId = generatedKeys.getInt(1);
                vote.setId(voteId);
            } else {
                throw new SQLException("Failed to obtain ID for the inserted vote.");
            }

            return vote;
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving vote", e);
        }
    }

    @Override
    public void delete(int id) {
        String deleteVoteByIdSQL = "DELETE FROM public.\"votes\" WHERE id = ?";

        try (PreparedStatement psDeleteVote = connection.prepareStatement(deleteVoteByIdSQL)) {
            psDeleteVote.setInt(1, id);
            psDeleteVote.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting vote with ID: " + id, e);
        }
    }

    @Override
    public Vote update(Vote vote, int id) {
        String updateVoteSQL = "UPDATE public.\"votes\" SET quiz_id = ?, student_id = ?, date = ?, result = ? WHERE id = ?";

        try (PreparedStatement psUpdateVote = connection.prepareStatement(updateVoteSQL)) {
            psUpdateVote.setInt(1, vote.getQuizID());
            psUpdateVote.setInt(2, vote.getStudentID());
            psUpdateVote.setDate(3, Date.valueOf(vote.getDate()));
            psUpdateVote.setDouble(4, vote.getResult());
            psUpdateVote.setInt(5, id);

            int rowsAffected = psUpdateVote.executeUpdate();
            if (rowsAffected > 0) {
                return vote;
            } else {
                throw new SQLException("No vote found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating vote with ID: " + id, e);
        }
    }

    @Override
    public Optional<Vote> findById(int id) {
        String findVoteByIdSQL = "SELECT * FROM public.\"votes\" WHERE id = ?";

        try (PreparedStatement psFindVote = connection.prepareStatement(findVoteByIdSQL)) {
            psFindVote.setInt(1, id);

            try (ResultSet rs = psFindVote.executeQuery()) {
                if (rs.next()) {
                    Vote vote = new Vote();
                    vote.setId(rs.getInt("id"));
                    vote.setQuizID(rs.getInt("quiz_id"));
                    vote.setStudentID(rs.getInt("student_id"));
                    vote.setDate(rs.getDate("date").toLocalDate());
                    vote.setResult(rs.getDouble("result"));

                    return Optional.of(vote);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding vote with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Vote> findAll() {
        String findAllVotesSQL = "SELECT * FROM public.\"votes\"";
        List<Vote> voteList = new ArrayList<>();

        try (PreparedStatement psFindAllVotes = connection.prepareStatement(findAllVotesSQL);
             ResultSet rs = psFindAllVotes.executeQuery()) {
            while (rs.next()) {
                Vote vote = new Vote();
                vote.setId(rs.getInt("id"));
                vote.setQuizID(rs.getInt("quiz_id"));
                vote.setStudentID(rs.getInt("student_id"));
                vote.setDate(rs.getDate("date").toLocalDate());
                vote.setResult(rs.getDouble("result"));

                voteList.add(vote);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all votes", e);
        }

        return voteList;
    }
}
