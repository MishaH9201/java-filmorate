package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmDbStorageTest {
    private final FilmStorage filmStorage;

    Film film = new Film(1, "Фильм", "Name",
            LocalDate.of(1921, 11, 11), 85, new Mpa(1, "G"), null);


    Film film1 = new Film(2, "Розовый фламинго", "Надо смотреть",
            LocalDate.of(1972, 10, 11), 85, new Mpa(5, "NC-17"), null);


    Film film2 = new Film(3, "Лак для волос", "Надо смотреть",
            LocalDate.of(1988, 11, 11), 85, new Mpa(5, "NC-17"), null);


    @Test
    @Order(1)
    public void shouldAddedAndGetByIdFilm() {
        filmStorage.addFilm(film);
        Film result = filmStorage.getFilmById(1);
        assertEquals(result, film);
    }

    @Test
    @Order(2)
    public void shouldUpdateFilm() {
        film.setDuration(2);
        filmStorage.updateFilm(film);
        Film result = filmStorage.getFilmById(1);
        assertEquals(result, film);
    }

    @Test
    @Order(3)
    public void shouldFindAllFilms() {
        film.setDuration(2);
        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);
        Collection<Film> result = filmStorage.findAll();
        Collection<Film> films = List.of(film, film1, film2);
        assertEquals(result, films);

    }

    @Test
    @Order(4)
    public void shouldDeleteFilm() {
        filmStorage.deleteFilm(1);
        Collection<Film> result = filmStorage.findAll();
        Collection<Film> films = List.of(film1, film2);
        assertEquals(result, films);

    }

}
