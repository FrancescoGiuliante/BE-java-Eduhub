package com.francesco_giuliante.infobasic.service;

import com.francesco_giuliante.infobasic.dao.WeekProgramDAO;
import com.francesco_giuliante.infobasic.model.WeekProgram;

import java.util.List;
import java.util.Optional;

public class WeekProgramService {
    private final WeekProgramDAO weekProgramDAO = new WeekProgramDAO();

    public WeekProgram createWeekProgram(WeekProgram weekProgram) {
        return weekProgramDAO.save(weekProgram);
    }

    public Optional<WeekProgram> getWeekProgramById(int id) {
        return weekProgramDAO.findById(id);
    }

    public List<WeekProgram> getAllWeekPrograms() {
        return weekProgramDAO.findAll();
    }

    public void deleteWeekProgram(int id) {
        weekProgramDAO.delete(id);
    }

    public WeekProgram updateWeekProgram(WeekProgram weekProgram, int id) {
        return weekProgramDAO.update(weekProgram, id);
    }
}
