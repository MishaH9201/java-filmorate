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
import java.util.Set;

@Service
public class FilmService {

    private final LikeDbStorage likeDbStorage;
    private final FilmStorage filmStorage;
    private final GenreDBStorage genreDBStorage;

    @Autowired
    public FilmService(LikeDbStorage likeDbStorage, FilmStorage filmStorage, GenreDBStorage genreDBStorage) {
        this.likeDbStorage = likeDbStorage;
        this.filmStorage = filmStorage;
        this.genreDBStorage = genreDBStorage;
    }

    public Film addFilm(Film film) {
        Validator.validate(film);
addGenres(film);
        return filmStorage.addFilm(film);

    }

    public void deleteFilm(Integer id) {
        filmStorage.deleteFilm(id);
        genreDBStorage.deleteFilm(id);
    }

    public Film updateFilm(Film film) {
        genreDBStorage.deleteFilm(film.getId());
addGenres(film);
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }


    public Film getFilmById(int id) {
        Film film=filmStorage.getFilmById(id);
        Set<Genre> genres=new HashSet<>();
        for(Genre genre: genreDBStorage.getFilmGenres(id)){
        genres.add(genre);
        }
        film.setGenres(genres);
        return film;
    }

    public void addLike(Integer filmId, Integer userId) {
        likeDbStorage.addLike(filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        likeDbStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {

        return likeDbStorage.getPopularFilms(count);
    }

    private void addGenres(Film film) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genreDBStorage.addFilmGenre(film.getId(), genre.getId());
            }
        }
    }
}




