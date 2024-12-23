package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    private int id;
    private List<Question> questions;
    private Integer ruleID;
    private Integer subjectID;
    private Integer professorID;
    private Integer attempts;
    private Double averageRating;
    private List<Integer> questionIDsValueX2 = new ArrayList<>();
    private List<Integer> questionIDsValueX3 = new ArrayList<>();
}
