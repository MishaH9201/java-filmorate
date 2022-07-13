package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.jdbc.support.KeyHolder;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public class FilmDbStorage implements FilmStorage {

    private  final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into FILMS(NAME, DESCRIPTION, DURATION,RELEASE_DATE,MPA_ID) " +
                "values (?, ?, ?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setInt(3, film.getDuration());
            final LocalDate relese = film.getReleaseDate();
            stmt.setDate(4, Date.valueOf(relese));
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        return film;
    }

    @Override
    public void deleteFilm(Integer id) {
        String sqlQuery = "delete from FILMS where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update FILMS set " +
                " NAME = ?, DESCRIPTION = ?, DURATION = ?,RELEASE_DATE = ?,MPA_ID = ? " +
                "where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getDuration()
                , Date.valueOf(film.getReleaseDate())
                , film.getMpa().getId()
                , film.getId());
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        final String sqlQuery = "select * from FILMS";
        final List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm);
        return films;
    }


    @Override
    public Film getFilmById(int id) {
        final String sqlQuery = "select * from FILMS where FILM_ID = ?";
        final List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, id);
        return films.get(0);
    }


   Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        int mpaId = rs.getInt("MPA_ID");
        String mpaName = "SELECT MPA_ID, NAME FROM MPA WHERE MPA_ID = ?";
        Mpa mpa = jdbcTemplate.query(mpaName, FilmDbStorage::makeMpa, mpaId);
        Film film = new Film();
        film.setId(rs.getInt("FILM_ID"));
        film.setName(rs.getString("NAME"));
        film.setDescription(rs.getString("DESCRIPTION"));
        film.setDuration(rs.getInt("DURATION"));
        film.setReleaseDate(rs.getDate("RELEASE_DATE").toLocalDate());
        film.setMpa(mpa);
        return film;
    }

    static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("MPA_ID"),
                rs.getString("NAME")
        );
    }
}

