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
public class Lesson {
    private int id;
    private int professorID;
    private int subjectID;
    private LocalDate date;
    private List<Integer> participations = new ArrayList<>();
    private int classID;

    public Lesson(int id, int professorID, int subjectID, LocalDate date, int classID) {
        this.id = id;
        this.professorID = professorID;
        this.subjectID = subjectID;
        this.date = date;
        this.classID = classID;
    }
}
