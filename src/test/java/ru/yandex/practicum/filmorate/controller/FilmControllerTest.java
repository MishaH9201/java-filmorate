package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {

    private static Validator validator;
    private final Film film = new Film();

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setFilm() {
        film.setDescription("В середине XIV века рыцарь Антониус Блок и его оруженосец возвращаются после " +
                "десяти лет крестовых походов в родную Швецию");
        film.setId(1);
        film.setName("Седьмая печать");
        film.setDuration(96);
        film.setReleaseDate(LocalDate.of(1957, 02, 16));
    }

    @Test
    public void shouldThrowsExceptionIfFilmNameIsEmpty() {
        film.setName("");
        Set<ConstraintViolation<Film>> violationSet = validator.validate(film);
        assertEquals("Name is empty", violationSet.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowsExceptionIfDescriptionLengthOver200() {
        film.setDescription("s".repeat(201));
        Set<ConstraintViolation<Film>> violationSet = validator.validate(film);
        assertEquals("Description over 200 characters", violationSet.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowsExceptionIfReleaseDateBefore28December1895() {
        film.setReleaseDate(LocalDate.of(1376, 11, 21));
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> FilmController.valid(film, new HashMap<>()));
        assertEquals("Wrong release date", exception.getMessage());
    }

    @Test
    public void shouldThrowsExceptionIfDurationIsNegative() {
        film.setDuration(-100);
        Set<ConstraintViolation<Film>> violationSet = validator.validate(film);
        assertEquals("Negative duration", violationSet.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowsExceptionIfIdIsNegative() {
        film.setId(-1);
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> FilmController.valid(film, new HashMap<>()));
        assertEquals("Negative ID", exception.getMessage());
    }
}
