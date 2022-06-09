package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.info("Users get");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, Errors error) {
        valid(user, users);
        if (error.hasErrors()) {
            throw new ValidationException();
        }
        users.put(user.getId(), user);
        log.info("User update");
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user, Errors error) {
        valid(user, users);
        if (error.hasErrors()) {
            throw new ValidationException();
        }
        users.put(user.getId(), user);
        log.info("User added");
        return user;
    }

    public static User valid(User user, Map<Integer, User> users) {
        int id = 0;
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Login contains spase");
        }
        if (user.getId() == 0) {
            id++;
            while (users.containsKey(id)) {
                id++;
            }
            user.setId(id);
        }
        if (user.getId() < 0) {
            throw new ValidationException("Negative ID");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}

