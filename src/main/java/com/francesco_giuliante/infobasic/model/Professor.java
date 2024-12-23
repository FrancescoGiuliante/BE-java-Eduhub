package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends Profile{
    private List<Integer> classIDs = new ArrayList<>();
    private List<Integer> participations = new ArrayList<>();
    private String biography;
    private List<Integer> questionIDs = new ArrayList<>();
    private List<Integer> quizIDs = new ArrayList<>();
    private List<Integer> ruleIDs = new ArrayList<>();

    public Professor(int id, int userId, String name, String lastName, String email, String biography) {
        super(id, userId, name, lastName, email);
        this.biography = biography;
    }

}
