package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.InstallerId;
import ru.yandex.practicum.filmorate.service.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film addFilm(Film film) {
        InstallerId.setId(film, films);
        Validator.validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteFilm(Integer id) {
        if (films.containsKey(id)) {
            films.remove(id);
        }
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            InstallerId.setId(film, films);
            Validator.validate(film);
            return addFilm(film);
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Id not found");
        }
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Film getFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Id not found");
        }
    }
}
