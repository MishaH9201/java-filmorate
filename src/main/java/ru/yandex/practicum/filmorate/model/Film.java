package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
        private  int id;
        @NotBlank(message = "Name is empty")
        private  String name;
        @Size(max=200,message = "Description over 200 characters")
        private  String description;
        private LocalDate releaseDate;
        @Positive(message = "Negative duration")
        private int duration;
        private Mpa mpa;
        private Set<Genre> genres;


}

