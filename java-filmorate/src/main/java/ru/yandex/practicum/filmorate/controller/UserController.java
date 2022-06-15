package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.serves.InstallerId;
import ru.yandex.practicum.filmorate.serves.UserService;
import ru.yandex.practicum.filmorate.serves.Validator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Users get");
        return users.values();
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        InstallerId.setId(user, users);
        Validator.validate(user);
        users.put(user.getId(), user);
        log.info("User update");
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        InstallerId.setId(user, users);
        Validator.validate(user);
        users.put(user.getId(), user);
        log.info("User added");
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId ){
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@Valid @RequestBody User user){
        return user;
    }

}

