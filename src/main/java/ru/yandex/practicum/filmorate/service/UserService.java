package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
public class
UserService {

    private final UserStorage userStorage;
    private final FriendDbStorage friendDbStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendDbStorage friendDbStorage) {
        this.userStorage = userStorage;
        this.friendDbStorage = friendDbStorage;
    }


    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public void deleteUser(Integer id) {
        userStorage.deleteUser(id);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public Collection<User> findAllUsers() {
        return userStorage.findAll();
    }



    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }
    
    public void addFriend(int id, int friendId) {
      friendDbStorage.addFriend(id,friendId);
    }

    public Collection<User> getAllFriends(int id) {
       return friendDbStorage.getAllFriends(id);
    }


    public Collection<User> getJointFriends(int id, Integer otherId) {
        return friendDbStorage.getJointFriends(id,otherId);
    }

    public void deleteFriend(int id, int friendId) {
        friendDbStorage.deleteFriend(id,friendId);
    }

   /* public User checksUsers(int id) {
        if (getUserById(id) != null) {
            return getUserById(id);
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Id is not found");
        }
    }*/

}
