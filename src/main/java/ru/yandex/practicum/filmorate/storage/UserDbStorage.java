package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        String sqlQuery = "insert into USERS (EMAIL, NAME, LOGIN,  BIRTHDAY) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getLogin());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthday));
            }
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }


    @Override
    public void deleteUser(Integer id) {
        String sqlQuery = "delete from USERS where USER_ID = ?";
        jdbcTemplate.update(sqlQuery, id);
    }


    @Override
    public User updateUser(User user) {
        getUserById(user.getId());
        String sqlQuery = "update USERS set " +
                "EMAIL = ?, NAME = ?, LOGIN = ?,BIRTHDAY = ? " +
                "where USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getEmail()
                , user.getName()
                , user.getLogin()
                , user.getBirthday()
                , user.getId());
        return user;
    }

    @Override
    public Collection<User> findAll() {
        final String sqlQuery = "select * from USERS ";
        final List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser);
        return users;
    }


    @Override
    public User getUserById(int id) {
        final String sqlQuery = "select * from USERS where USER_ID = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, id);
        if(users==null || users.isEmpty()){
            throw new ValidationException(HttpStatus.NOT_FOUND, "User not found");
        }
        return users.get(0);
    }

    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("USER_ID"),
                rs.getString("EMAIL"),
                rs.getString("LOGIN"),
                rs.getString("NAME"),
                rs.getDate("BIRTHDAY").toLocalDate());
    }
}
