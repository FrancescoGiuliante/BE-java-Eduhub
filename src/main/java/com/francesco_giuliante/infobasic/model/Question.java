package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private int id;
    private String prompt;
    private List<String> answers;
    private String correctAnswer;
    private int wrongs;
    private int rights;
    private int professorID;
}
