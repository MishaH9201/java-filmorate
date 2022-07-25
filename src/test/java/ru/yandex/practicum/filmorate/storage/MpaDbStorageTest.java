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
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;

    @Test
    public void shouldGetMpaById() {
        Mpa result = mpaDbStorage.getMpaById(1);
        assertEquals(result, new Mpa(1, "G"));
    }

    @Test
    public void shouldGetAllMpa() {
        Collection<Mpa> result = mpaDbStorage.findAll();
        Collection<Mpa> mpaCollection = List.of(
                new Mpa(1, "G"),
                new Mpa(2, "PG"),
                new Mpa(3, "PG-13"),
                new Mpa(4, "R"),
                new Mpa(5, "NC-17")
        );
        assertEquals(result, mpaCollection);
    }
    @Test
    public void shouldThrowsExceptionIfIdIsNotFound() {
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> mpaDbStorage.getMpaById(19));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
