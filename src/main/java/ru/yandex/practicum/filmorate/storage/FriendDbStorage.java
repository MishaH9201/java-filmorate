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
        String sqlQuery = "INSERT INTO friends (user_id, friend_id) VALUES  (?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    public Collection<User> getAllFriends(int id) {
        List<User> friends = new ArrayList<>();
        SqlRowSet srs = jdbcTemplate
                .queryForRowSet("SELECT * FROM users " +
                        "WHERE user_id IN " +
                        "(SELECT friend_id FROM FRIENDS " +
                        "WHERE user_id = ?)", id);
        while (srs.next()) {
            friends.add(new User(srs.getInt("user_id"),
                    srs.getString("email"),
                    srs.getString("login"),
                    srs.getString("name"),
                    srs.getDate("birthday").toLocalDate()));
        }
        return friends;
    }

    public Collection<User> getJointFriends(int id, Integer otherId) {
        List<User> friends = new ArrayList<>();
        SqlRowSet srs = jdbcTemplate.queryForRowSet("SELECT * FROM users u WHERE u.user_id IN " +
                "(SELECT friend_id FROM FRIENDS f " +
                "WHERE f.user_id = ?) " +
                "AND u.user_id IN (SELECT friend_id FROM friends fs " +
                "WHERE fs.user_id = ?)", id, otherId);
        while (srs.next()) {
            friends.add(new User(srs.getInt("user_id"),
                    srs.getString("email"),
                    srs.getString("login"),
                    srs.getString("name"),
                    srs.getDate("birthday").toLocalDate()));
        }
        return friends;
    }

    public void deleteFriend(int id, int friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }


}
