package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {
    UserStorage userStorage;
    FilmStorage filmStorage;

    @Autowired
    public FilmService(UserStorage userStorage, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Film addLike(Integer filmId, Integer userId) {
            Film film = checksFilmsAndUsers(filmId,userId);
            film.addLike(userId);
            return film;

    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = checksFilmsAndUsers(filmId, userId);
        if (film.getLikes().contains(userId)) {
            film.deleteLike(userId);
        }else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Like does not exist");
        }
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.findAll().stream()
                .sorted((o1, o2) -> {
                    if (o1.getLikes().size() < o2.getLikes().size()) {
                        return 1;
                    } else {
                        return -1;
                    }
                })
                .limit(count)
                .collect(Collectors.toList());
    }


    public Film checksFilmsAndUsers(Integer filmId, Integer userId) {
        if (filmStorage.getFilms().containsKey(filmId) && userStorage.getUsers().containsKey(userId)) {
            return filmStorage.getFilms().get(filmId);
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Id is not found");
        }
    }


}
