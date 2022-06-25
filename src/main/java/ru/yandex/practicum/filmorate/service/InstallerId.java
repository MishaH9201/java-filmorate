package ru.yandex.practicum.filmorate.service;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;


public class InstallerId {
    private static int id;

    public static User setId(User user, Map<Integer, User> users) {
        if (user.getId() == 0) {
            id=1;
            while (users.containsKey(id)) {
                id++;
            }
            user.setId(id);
        }
        if (0 > user.getId()) {

            throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR, "Negative ID");
        }
        return user;
    }

    public static Film setId(Film film, Map<Integer, Film> films) {
        if (film.getId() == 0) {
            id=1;
            while (films.containsKey(id)) {
                id++;
            }
            film.setId(id);
        }
        if (0 > film.getId()) {
            throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR, "Negative ID");
        }
        return film;
    }
}

