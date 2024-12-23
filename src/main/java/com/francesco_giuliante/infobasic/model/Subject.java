package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    private int id;
    private String name;
    private String description;
    private List<Integer> professorIDs = new ArrayList<>();
    private List<Integer> quizIDs = new ArrayList<>();

    public Subject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.professorIDs = new ArrayList<>();
        this.quizIDs = new ArrayList<>();
    }
}
