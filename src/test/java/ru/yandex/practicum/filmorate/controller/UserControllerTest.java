package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserControllerTest {
    private static Validator validator;
    private final User user = new User();
    private final UserService userService;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setFilm() {
        user.setLogin("АнтониусБлок");
        user.setId(1);
        user.setEmail("m@m.com");
        user.setBirthday(LocalDate.of(1612, 02, 16));
    }

    @Test
    public void shouldThrowsExceptionIfLoginIsEmpty() {
        user.setLogin("");
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        assertEquals("Login is empty", violationSet.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowsExceptionIfEmailIsInvalid() {
        user.setEmail("s");
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        assertEquals("Wrong format email", violationSet.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowsExceptionIfBirthdayInTheFuture() {
        user.setBirthday(LocalDate.of(2376, 11, 21));
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        assertEquals("User's birthday in the future", violationSet.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowsExceptionIfLoginContainsSpace() {
        user.setLogin("Антониус Блок");
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> ru.yandex.practicum.filmorate.service.Validator.validate(user));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void shouldThrowsExceptionIfIdIsNegative() {
        user.setId(-1);
        final ValidationException exception = assertThrows(ValidationException.class,
                () -> userService.addUser(user));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

}
