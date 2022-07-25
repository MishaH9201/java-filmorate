package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {

    public static User validate(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST , "Login contains spase");
        }
        if(user.getId()<0){
            throw new ValidationException(HttpStatus.BAD_REQUEST , "Id is negative");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }

    public static Film validate(Film film) {
        final LocalDate beginningOfCinema = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(beginningOfCinema)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Wrong release date");
        }
        if(film.getId()<0){
            throw new ValidationException(HttpStatus.BAD_REQUEST , "Id is negative");
        }
        return film;
    }

}

