package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

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
        return genres.get(0);
    }

    public Genre updateGenre(Genre genre) {
        String sqlQuery = "update GENRES set " +
                " NAME = ? " +
                "where GENRE_ID = ?";
        jdbcTemplate.update(sqlQuery
                , genre.getName());
        return genre;
    }
    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("NAME")
                );
}
}