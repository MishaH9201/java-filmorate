package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreDBStorage;
import ru.yandex.practicum.filmorate.storage.LikeDbStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final LikeDbStorage likeDbStorage;
    private final FilmStorage filmStorage;
    private final GenreDBStorage genreDBStorage;
    private final UserService userService;

    @Autowired
    public FilmService(LikeDbStorage likeDbStorage, FilmStorage filmStorage,
                       GenreDBStorage genreDBStorage, UserService userService) {
        this.likeDbStorage = likeDbStorage;
        this.filmStorage = filmStorage;
        this.genreDBStorage = genreDBStorage;
        this.userService = userService;
    }

    public Film addFilm(Film film) {
        Validator.validate(film);
        return filmStorage.addFilm(film);

    }

    public void deleteFilm(Integer id) {
        filmStorage.deleteFilm(id);

    }

    public Film updateFilm(Film film) {
        genreDBStorage.deleteFilm(film.getId());
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }


    public Film getFilmById(int id) {
        Film film = filmStorage.getFilmById(id);
        film.setGenres(new LinkedHashSet<Genre>(genreDBStorage.getFilmGenres(id))
        );
        return film;
    }

    public void addLike(Integer filmId, Integer userId) {
        filmStorage.getFilmById(filmId);
        userService.getUserById(userId);
        likeDbStorage.addLike(filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        filmStorage.getFilmById(filmId);
        userService.getUserById(userId);
        likeDbStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return likeDbStorage.getPopularFilms(count);
    }
}






