package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends Profile {
    private List<Integer> voteIDs = new ArrayList<>();
    private List<Integer> classIDs = new ArrayList<>();
    private List<Integer> participations = new ArrayList<>();

    public Student(int id, int userId, String name, String lastName, String email) {
        super(id, userId, name, lastName, email);
    }

}
