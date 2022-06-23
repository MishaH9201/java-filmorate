package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(UserStorage userStorage, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public void deleteFilm(Integer id) {
        filmStorage.deleteFilm(id);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Map<Integer, Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public Film addLike(Integer filmId, Integer userId) {
        Film film = checksFilmsAndUsers(filmId, userId);
        film.addLike(userId);
        return film;

    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = checksFilmsAndUsers(filmId, userId);
        if (film.getLikes().contains(userId)) {
            film.deleteLike(userId);
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Like does not exist");
        }
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.findAll().stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film checksFilmsAndUsers(Integer filmId, Integer userId) {
        if (getFilmById(filmId) != null && userStorage.getUserById(userId) != null) {
            return getFilmById(filmId);
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Id is not found");
        }
    }
}
