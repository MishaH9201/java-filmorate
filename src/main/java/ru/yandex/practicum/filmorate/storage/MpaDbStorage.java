package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
@Repository
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Mpa> findAll() {
        final String sqlQuery = "select * from MPA";
        final List<Mpa> mpa = jdbcTemplate.query(sqlQuery, MpaDbStorage::makeMpa);
        return mpa;
    }

    public Mpa getMpaById(int id) {
        final String sqlQuery = "select * from MPA where MPA_ID = ?";
        final List<Mpa> mpa = jdbcTemplate.query(sqlQuery, MpaDbStorage::makeMpa, id);
        return mpa.get(0);
    }


    static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("MPA_ID"),
                rs.getString("NAME")
        );
    }
}
