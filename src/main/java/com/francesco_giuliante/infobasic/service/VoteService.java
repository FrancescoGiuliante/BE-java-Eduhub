package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.VoteDAO;
import com.francesco_giuliante.infobasic.model.Vote;

import java.util.List;
import java.util.Optional;

public class VoteService {
    private final VoteDAO voteDAO = new VoteDAO();

    public Vote createVote(Vote vote) {
        return voteDAO.save(vote);
    }

    public Optional<Vote> getVoteById(int id) {
        return voteDAO.findById(id);
    }

    public List<Vote> getAllVotes() {
        return voteDAO.findAll();
    }

    public void deleteVote(int id) {
        voteDAO.delete(id);
    }

    public Vote updateVote(Vote vote, int id) {
        return voteDAO.update(vote, id);
    }
}
