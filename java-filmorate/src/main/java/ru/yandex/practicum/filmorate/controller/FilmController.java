package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmService filmService, FilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Films get");
        return filmStorage.findAll();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable Integer id) {
        log.info("Films get");
        return filmStorage.getFilmById(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Film added");
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        log.info("Film update");
        return filmStorage.putFilm(film);
    }

    @DeleteMapping(value = {"/{id}", "/{id}/like/{userId}"})
    public void deleteFilmOrLike(@PathVariable Integer id, @PathVariable(required = false) Integer userId) {
        if (userId != null) {
            log.info("Delete like");
            filmService.deleteLike(id,userId);
        } else {
            log.info("Delete film");
            filmStorage.deleteFilm(id);
        }
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Integer id, @PathVariable Integer userId){
        log.info("Like added");
        return filmService.addLike(id,userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms( @RequestParam(defaultValue = "10", required = false) Integer count){
        log.info("Popular films get");
        return filmService.getPopularFilms(count);
    }
}
