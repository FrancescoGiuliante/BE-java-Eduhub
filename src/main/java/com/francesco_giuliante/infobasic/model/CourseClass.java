package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseClass {
    private int id;
    private String path;
    private String name;
    private List<Integer> studentIDs = new ArrayList<>();
    private List<Integer> professorIDs = new ArrayList<>();
    private List<Integer> subjectIDs = new ArrayList<>();
    private String syllabus;
    private int courseID;
    private List<Integer> weekProgramIDs = new ArrayList<>();

    public CourseClass(int id, String path, String name, String syllabus, int courseID) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.syllabus = syllabus;
        this.courseID = courseID;
    }
}
