package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage =userStorage;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Users get");
        return userStorage.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return userStorage.getUserById(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userStorage.addUser(user);
        log.info("User update");
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        userStorage.putUser(user);
        log.info("User added");
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Integer id, @PathVariable Integer friendId ){
        log.info("Friend added");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = {"/{id}", "/{id}/friends/{friendId}"})
    public void deleteUserOrFriend(@PathVariable Integer id, @PathVariable(required = false) Integer friendId) {
        if (friendId != null) {
            log.info("Delete friend");
            userService.deleteFriend(id,friendId);
        } else {
            log.info("Delete user");
            userStorage.deleteUser(id);
        }
    }

    @GetMapping(value = {"/{id}/friends", "/{id}/friends/common/{otherId}"} )
    public Collection<User> getFriends(@PathVariable Integer id, @PathVariable(required = false) Integer  otherId ){
        if(otherId != null){
            log.info("Friend get");
          return userService.getJointFriends(id, otherId);
        }else {
            log.info("Friends get");
            return userService.getAllFriends(id);
        }
    }
}

