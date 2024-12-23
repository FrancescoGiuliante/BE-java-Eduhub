package com.francesco_giuliante.infobasic.service;


import com.francesco_giuliante.infobasic.dao.RuleDAO;
import com.francesco_giuliante.infobasic.model.Rule;

import java.util.List;
import java.util.Optional;

public class RuleService {
    private static RuleDAO ruleDAO = new RuleDAO();

    public List<Rule> getAllRules() {
        return ruleDAO.findAll();
    }

    public Optional<Rule> getRuleById(int id) {
        return ruleDAO.findById(id);
    }

    public List<Rule> getAllProfessorRules(int professorID) {
        return ruleDAO.findAllByProfessorID(professorID);
    }

    public Rule createRule(Rule rule) {
        return ruleDAO.save(rule);
    }

    public Rule updateRule(Rule rule, int id) {
        return ruleDAO.update(rule, id);
    }

    public void deleteRule(int id) {
        ruleDAO.delete(id);
    }
}
