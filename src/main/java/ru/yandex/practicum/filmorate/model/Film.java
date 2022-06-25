package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
        private Set<Integer> likes=new HashSet<>();

        public void addLike(int id){
                likes.add(id);
        }
        public void deleteLike(int id){
                likes.remove(id);
        }
    }

