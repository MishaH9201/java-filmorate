package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class
UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(int id, int friendId){
            User user = checksUsers(id);
            User userFriend=checksUsers(friendId);
            user.addFriend(friendId);
            userFriend.addFriend(id);
            return user;
    }

    public Collection<User> getAllFriends(int id) {
       User user = checksUsers(id);
        return   user
                .getFriends()
                .stream()
                .map(p -> userStorage.getUsers().get(p))
                .collect(Collectors.toList());
        }


    public Collection<User> getJointFriends(int id, Integer otherId) {
        checksUsers(id);
        checksUsers(otherId);
            return getAllFriends(id)
                    .stream().filter(p -> p.getFriends()
                            .contains(otherId))
                    .collect(Collectors.toList());
    }

    public void deleteFriend(int id,int friendId) {
            User user = checksUsers(id);
            User userFriend=checksUsers(friendId);
            if(user.getFriends().contains(friendId)) {
                user.deleteFriend(friendId);
                userFriend.deleteFriend(id);
            }
    }

public User checksUsers(int id){
    Map users= userStorage.getUsers();
    if(users.containsKey(id)){
        return (User) users.get(id);
    }else{
        throw new ValidationException(HttpStatus.NOT_FOUND,"Id is not found");
    }
}

}
