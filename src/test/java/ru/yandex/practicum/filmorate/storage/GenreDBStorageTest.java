package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenreDBStorageTest {

    private final GenreDBStorage genreDBStorage;
    private final FilmStorage filmStorage;

    Film film = new Film(1, "Фильм", "Name",
            LocalDate.of(1921, 11, 11), 85, new Mpa(1, "G"), null);

    @Test
    @Order(1)
    public void shouldGetGenresById() {
        Genre result = genreDBStorage.getGenreById(1);
        assertEquals(result, new Genre(1, "Комедия"));
    }

    @Test
    @Order(2)
    public void shouldGetAllGenres() {
        Collection<Genre> result = genreDBStorage.findAll();
        Collection<Genre> genres = List.of(
                new Genre(1, "Комедия"),
                new Genre(2, "Драма"),
                new Genre(3, "Мультфильм"),
                new Genre(4, "Триллер"),
                new Genre(5, "Документальный"),
                new Genre(6, "Боевик")
        );
        assertEquals(result, genres);
    }

    @Test
    @Order(3)
    public void shouldThrowsExceptionIfIdIsNotFound() {
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> genreDBStorage.getGenreById(19));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @Order(4)
    public void shouldAddFilmGenresAndGetGenres() {
        filmStorage.addFilm(film);
        genreDBStorage.addFilmGenre(film.getId(), 1);
        genreDBStorage.addFilmGenre(film.getId(), 5);
        Collection<Genre> genres = List.of(
                genreDBStorage.getGenreById(1),
                genreDBStorage.getGenreById(5)
        );
        Collection<Genre> result = genreDBStorage.getFilmGenres(film.getId());
        assertEquals(result, genres);
    }

    @Test
    @Order(5)
    public void shouldEmptyListIfGetDeletedFilm() {
        filmStorage.deleteFilm(film.getId());
        Collection<Genre> genres = new ArrayList<>();
        Collection<Genre> result = genreDBStorage.getFilmGenres(film.getId());
        assertEquals(result, genres);
    }
}
