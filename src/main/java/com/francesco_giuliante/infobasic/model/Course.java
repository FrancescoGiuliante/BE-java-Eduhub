package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private int id;
    private String description;
    private String path;
    private List<Integer> professorIDs = new ArrayList<>();
    private List<Integer> classIDs = new ArrayList<>();

    public Course(int id, String description, String path) {
        this.id = id;
        this.description = description;
        this.path = path;
    }
}
