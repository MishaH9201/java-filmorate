package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.serves.InstallerId;
import ru.yandex.practicum.filmorate.serves.Validator;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private static int id = 0;

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Films get");
        return films.values();

    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        InstallerId.setId(film, films);
        Validator.validate(film);
        films.put(film.getId(), film);
        log.info("Film added");
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        InstallerId.setId(film, films);
        Validator.validate(film);
        films.put(film.getId(), film);
        log.info("Film update");
        return film;
    }
}
