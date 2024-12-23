package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    private int id;
    private List<Integer> quizIDs = new ArrayList<>();
    private int professorID;
    private double valueRightAnswer;
    private double valueWrongAnswer;
    private int duration;
}