package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {

    User addUser(User user);

    void deleteUser(Integer id);

    User updateUser(User user);

    Collection<User> findAll();

    Map<Integer, User> getUsers();

    User getUserById(int id);
}
