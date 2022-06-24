package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

public interface FilmStorage {
    Film addFilm(Film film);

    void deleteFilm(Integer id);

    Film updateFilm(Film film);

    Collection<Film> findAll();

    Map<Integer, Film> getFilms();

    Film getFilmById(int id);
}
