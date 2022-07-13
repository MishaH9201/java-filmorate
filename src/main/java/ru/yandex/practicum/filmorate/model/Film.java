package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
        @PositiveOrZero
        private  int id;
        @NotBlank(message = "Name is empty")
        private  String name;
        @Size(max=200,message = "Description over 200 characters")
        private  String description;
        private LocalDate releaseDate;
        @Positive(message = "Negative duration")
        private int duration;
        private Mpa mpa;

        public Film(int film_id, String name, String description, LocalDate release_date) {
        }
}

