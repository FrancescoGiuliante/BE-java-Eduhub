package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.ProfessorDAO;
import com.francesco_giuliante.infobasic.model.Professor;

import java.util.List;
import java.util.Optional;

public class ProfessorService {
    private final ProfessorDAO professorDAO = new ProfessorDAO();

    public Professor createProfessor(Professor professor) {
        return professorDAO.save(professor);
    }

    public Optional<Professor> getProfessorById(int id) {
        return professorDAO.findById(id);
    }

    public Optional<Professor> getProfessorByUserId(int userId) {
        return professorDAO.findByUserId(userId);
    }

    public List<Professor> getAllProfessors() {
        return professorDAO.findAll();
    }

    public void deleteProfessor(int id) {
        professorDAO.delete(id);
    }

    public Professor updateProfessor(Professor professor, int id) {
        return professorDAO.update(professor, id);
    }
}
