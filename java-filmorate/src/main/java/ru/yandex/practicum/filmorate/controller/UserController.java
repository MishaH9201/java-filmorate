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
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserController(UserService userService, InMemoryUserStorage inMemoryUserStorage) {
        this.userService = userService;
        this.inMemoryUserStorage=inMemoryUserStorage;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Users get");
        return inMemoryUserStorage.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return inMemoryUserStorage.getUserById(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
       // InstallerId.setId(user, users);
       // Validator.validate(user);
       // users.put(user.getId(), user);
        inMemoryUserStorage.addUser(user);
        log.info("User update");
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
       // InstallerId.setId(user, users);
      //  Validator.validate(user);
    //    users.put(user.getId(), user);
        inMemoryUserStorage.putUser(user);
        log.info("User added");
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId ){
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
         inMemoryUserStorage.deleteUser(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId){
        userService.deleteFriend(id,friendId);
    }

    @GetMapping(value = {"/{id}/friends", "/{id}/friends/common/{otherId}"} )
    public Collection<User> getFriends(@PathVariable int id, @PathVariable(required = false) Integer  otherId ){
        if(otherId != null){
          return userService.getJointFriends(id, otherId);
        }
    return userService.getAllFriends(id);
    }


}

