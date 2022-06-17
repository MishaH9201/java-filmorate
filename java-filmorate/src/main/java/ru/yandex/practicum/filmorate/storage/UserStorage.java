package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User addUser(User user);

    void deleteUser(Integer id);

    User putUser(User user);

    Collection<User> findAll();

    User getUserById(int id);
}
