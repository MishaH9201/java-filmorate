package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {

        private  int id;
        @NotBlank(message = "Name is empty")
        private  String name;
        @Size(max=200,message = "Description over 200 characters")
        private  String description;
        private LocalDate releaseDate;
        @Positive(message = "Negative duration")
        private int duration;
    }

