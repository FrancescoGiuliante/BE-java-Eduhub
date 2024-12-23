package com.francesco_giuliante.infobasic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Profile {
    private int id;
    private int userId;
    private String name;
    private String lastName;
    private String email;
}
