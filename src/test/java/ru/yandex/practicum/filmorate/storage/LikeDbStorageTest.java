package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
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
public class LikeDbStorageTest {

 private final UserStorage userStorage;
 private final LikeDbStorage likeDbStorage;
 private final FilmStorage filmStorage;

    User user = new User(1,"mail@mail.ru","nic","Олег",
            LocalDate.of(1980,11,11));

    User user1 = new User(2,"dr@mail.ru","Pet","Петя",
            LocalDate.of(1990,11,21));

    User user2 = new User(3,"l@mail.ru","Vek","Коля",
            LocalDate.of(1999,1,1));

    Film film = new Film(1, "Фильм", "Name",
            LocalDate.of(1921, 11, 11), 85, new Mpa(1, "G"), null);

    Film film1 = new Film(2, "Розовый фламинго", "Надо смотреть",
            LocalDate.of(1972, 10, 11), 85, new Mpa(5, "NC-17"), null);


    Film film2 = new Film(3, "Лак для волос", "Надо смотреть",
            LocalDate.of(1988, 11, 11), 85, new Mpa(5, "NC-17"), null);

    @Test
    @Order(1)
    public void shouldAddAndGetPopularFilms() {
        filmStorage.addFilm(film);
        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);
        userStorage.addUser(user);
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        likeDbStorage.addLike(film.getId(),user.getId());
        likeDbStorage.addLike(film1.getId(),user.getId());
        likeDbStorage.addLike(film1.getId(),user1.getId());
        likeDbStorage.addLike(film1.getId(),user2.getId());
        likeDbStorage.addLike(film2.getId(),user.getId());
        likeDbStorage.addLike(film2.getId(),user1.getId());
        Collection<Film> result=likeDbStorage.getPopularFilms(3);
        Collection<Film> films= List.of(film1, film2, film);
        assertEquals(result, films);
    }

    @Test
    @Order(1)
    public void shouldDeleteLike() {
        likeDbStorage.deleteLike(film2.getId(),user.getId());
        likeDbStorage.deleteLike(film2.getId(),user1.getId());
        Collection<Film> result=likeDbStorage.getPopularFilms(3);
        Collection<Film> films= List.of(film1, film, film2);
        assertEquals(result, films);
    }
}
