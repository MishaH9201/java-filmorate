package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

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

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Films get");
        return films.values();

    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film , Errors error) {
        valid(film,films);
        if(error.hasErrors()){
            throw new ValidationException();
        }
        films.put(film.getId(), film);
log.info("Film added");
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film, Errors error) {
        valid(film, films);
        if(error.hasErrors()){
            throw new ValidationException();
        }
        films.put(film.getId(), film);
        log.info("Film update");
        return film;
    }

    public static Film valid(Film film,Map<Integer,Film> films){
        int id=0;
        final LocalDate beginningOfCinema=LocalDate.of(1895,12,28);
        if(film.getReleaseDate().isBefore(beginningOfCinema)){
            throw new ValidationException("Wrong release date");
        }
        if(film.getId()==0){
            id++;
            while(films.containsKey(id)){
                id++;
            }
            film.setId(id);
        }
        if(film.getId()<0){
            throw new ValidationException("Negative ID");
        }
        return film;
    }

}
