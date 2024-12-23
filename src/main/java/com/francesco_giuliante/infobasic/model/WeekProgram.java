package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekProgram {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Integer> lessonIDs = new ArrayList<>();
    private int classID;

    public WeekProgram(int id, LocalDate startDate, LocalDate endDate, int classID) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.classID = classID;
    }
}
