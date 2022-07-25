package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
public class GenreDBStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Genre> findAll() {
        final String sqlQuery = "SELECT * FROM genres";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDBStorage::makeGenre);
        return genres;
    }

    public Genre getGenreById(int id) {
        final String sqlQuery = "SELECT * FROM genres WHERE genre_id = ?";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDBStorage::makeGenre, id);
        if (genres == null || genres.isEmpty()) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Genre not found");
        }
        return genres.get(0);
    }


    public void addFilmGenre(Integer filmId, Integer genreId) {
        String sqlQuery = "MERGE INTO film_genres(film_id, genre_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }


    public Collection<Genre> getFilmGenres(Integer filmId) {
        final String sqlQuery = "SELECT * FROM genres AS i " +
                "RIGHT JOIN film_genres AS a  ON i.genre_id = a.genre_id " +
                "WHERE a.film_id = ?";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDBStorage::makeGenre, filmId);
        return genres;
    }

    public void deleteFilm(Integer id) {
        final String sqlQuery = "DELETE FROM film_genres WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("NAME")
        );
    }


}