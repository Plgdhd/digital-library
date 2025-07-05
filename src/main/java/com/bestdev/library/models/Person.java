package com.bestdev.library.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Person {

    private int id;
    
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 150, message = "Имя должно быть в пределах от 2 символов до 150")
    private String name;

    @Min(value = 1)
    @Max(value = 2025)
    private int birthday;

    public Person(){

    }

    public Person(int id, String name, int birthday){
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }
}
