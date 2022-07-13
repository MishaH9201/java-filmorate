package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeDbStorage;

import java.util.Collection;

@Service
public class FilmService {

    private final LikeDbStorage likeDbStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(LikeDbStorage likeDbStorage, FilmStorage filmStorage) {
        this.likeDbStorage = likeDbStorage;
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public void deleteFilm(Integer id) {
        filmStorage.deleteFilm(id);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }



    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public void addLike(Integer filmId, Integer userId) {
likeDbStorage.addLike(filmId,userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
likeDbStorage.deleteLike(filmId,userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {

        return likeDbStorage.getPopularFilms(count);
    }
    }





