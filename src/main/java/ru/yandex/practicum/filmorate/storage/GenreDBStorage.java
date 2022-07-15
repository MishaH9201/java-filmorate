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
        final String sqlQuery = "select * from GENRES ";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDBStorage::makeGenre);
        return genres;
    }

    public Genre getGenreById(int id) {
        final String sqlQuery = "select * from GENRES where GENRE_ID = ?";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDBStorage::makeGenre, id);
        if (genres == null || genres.isEmpty()) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Genre not found");
        }
        return genres.get(0);
    }

    public Genre updateGenre(Genre genre) {
        getGenreById(genre.getId());
        String sqlQuery = "update GENRES set " +
                " NAME = ? " +
                "where GENRE_ID = ?";
        jdbcTemplate.update(sqlQuery
                , genre.getName());
        return genre;
    }

    public void addFilmGenre(Integer filmId, Integer genreId) {
            String sqlQuery = "merge into FILM_GENRES(FILM_ID, GENRE_ID) " +
                    "values (?, ?)";
            jdbcTemplate.update(sqlQuery, filmId, genreId);
        }


    public Collection<Genre> getFilmGenres(Integer filmId) {
        final String sqlQuery = "select * from GENRES AS i " +
                "RIGHT JOIN FILM_GENRES AS a  ON i.GENRE_ID = a.GENRE_ID " +
                "WHERE a.FILM_ID = ?";
        final List<Genre> g = jdbcTemplate.query(sqlQuery, GenreDBStorage::makeGenre, filmId);
        return g;
    }

    public void deleteFilm(Integer id) {
        final String sqlQuery = "delete from FILM_GENRES where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("NAME")
        );
    }


}