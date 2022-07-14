package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class LikeDbStorage {
        private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(Integer filmId, Integer userId) {
        String sql = "INSERT INTO LIKES (FILM_ID, USER_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        String sql = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT F.FILM_ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, F.MPA_ID, " +
                "COUNT(U.USER_ID) AS TOP_LIKED FROM FILMS F " +
                "LEFT OUTER JOIN LIKES U ON F.FILM_ID = U.FILM_ID " +
                "GROUP BY F.FILM_ID ORDER BY TOP_LIKED DESC LIMIT ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sql ,count );
        while (srs.next()) {
            int mpaId = srs.getInt("MPA_ID");
            String mpaName = "SELECT MPA_ID, NAME FROM MPA WHERE MPA_ID = ?";
            Mpa mpa = jdbcTemplate.query(mpaName, FilmDbStorage::makeMpa, mpaId).get(0);
            Film film = new Film();
            film.setId(srs.getInt("FILM_ID"));
            film.setName(srs.getString("NAME"));
            film.setDescription(srs.getString("DESCRIPTION"));
            film.setDuration(srs.getInt("DURATION"));
            film.setReleaseDate(srs.getDate("RELEASE_DATE").toLocalDate());
            film.setMpa(mpa);
            films.add(film);
        }
        return films;
    }
}
