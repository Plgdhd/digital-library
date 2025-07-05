package com.bestdev.library.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Book {
    
    private int id;
    
    @NotEmpty(message = "Name couldnt be empty")
    @Size(min = 1, max = 150, message = "Title should be in range 2 to 150 symbols")
    private String title;

    @Size(min = 1, max = 150, message = "Author name should be in range 2 to 150 symbols")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, [A-Z]\\w+", message = "Format: Pushkin Alexander Sergeevich")
    private String author;

    @Min(value = 1, message = "year couldn`t be lower than 1")
    @Max(value = 2025, message = "year couldn`t be higher than 2025")
    private int year; 
    

}
