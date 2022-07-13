package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class FriendDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) throws ValidationException {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(Integer id, Integer friendId) {
        String sqlQuery = "insert into FRIENDS (USER_ID, FRIEND_ID) values (?, ?)";
        jdbcTemplate.update(sqlQuery);
    }


    public Collection<User> getAllFriends(int id) {
        List<User> friends = new ArrayList<>();
        SqlRowSet srs = jdbcTemplate
                .queryForRowSet("SELECT * FROM USERS " +
                        "WHERE USERS_ID IN " +
                        "(SELECT FRIEND_ID FROM FRIENDS " +
                        "WHERE USER_ID = ?)", id);
        while (srs.next()) {
            friends.add(new User(srs.getInt("USER_ID"),
                    srs.getString("EMAIL"),
                    srs.getString("LOGIN"),
                    srs.getString("NAME"),
                    srs.getDate("BIRTHDAY").toLocalDate()));
        }
        return friends;
    }




    public Collection<User> getJointFriends(int id, Integer otherId) {
        List<User> friends = new ArrayList<>();
        SqlRowSet srs = jdbcTemplate.queryForRowSet("SELECT * FROM USERS U WHERE U.USER_ID IN " +
                "(SELECT FRIEND_ID FROM FRIENDS F " +
                "WHERE F.USER_ID = ?) " +
                "AND U.USER_ID IN (SELECT FRIEND_ID FROM FRIENDS FS " +
                "WHERE FS.USER_ID = ?)", id, otherId);
        while (srs.next()) {
            friends.add(new User(srs.getInt("USER_ID"),
                    srs.getString("EMAIL"),
                    srs.getString("LOGIN"),
                    srs.getString("NAME"),
                    srs.getDate("BIRTHDAY").toLocalDate()));
        }
        return friends;
    }

    public void deleteFriend(int id, int friendId) {
        String sqlQuery = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery,id,friendId);
    }



}
