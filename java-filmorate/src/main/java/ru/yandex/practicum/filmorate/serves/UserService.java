package ru.yandex.practicum.filmorate.serves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    InMemoryUserStorage inMemoryUserStorage;

    public User addFriend(int id, int friendId){
        Map users= inMemoryUserStorage.getUsers();
        if(users.containsKey(id) && users.containsKey(friendId)){
            User user = (User) users.get(id);
            User userFriend=(User) users.get(friendId);
            user.addFriend(friendId);
            userFriend.addFriend(id);
            return user;
        }else{
            throw new RuntimeException();
        }

    }

    public Collection<User> getAllFriends(int id) {
        Map users= inMemoryUserStorage.getUsers();
        if(users.containsKey(id)){
        return  inMemoryUserStorage.getUsers().get(id)
                .getFriends()
                .stream()
                .map(p -> inMemoryUserStorage.getUsers().get(p))
                .collect(Collectors.toList());
    }else {
            throw new ValidationException(HttpStatus.NOT_FOUND,"er");
        }
        }


    public Collection<User> getJointFriends(int id, Integer otherId) {
        return getAllFriends(id).stream().filter(p -> p.getFriends().contains(otherId)).collect(Collectors.toList());
    }

    public void deleteFriend(int id,int friendId) {
        Map users= inMemoryUserStorage.getUsers();
        if(users.containsKey(id) && users.containsKey(friendId)){
            User user = (User) users.get(id);
            User userFriend=(User) users.get(friendId);
            user.getFriends().remove(friendId);
            userFriend.getFriends().remove(id);
        }else{
            throw new ValidationException(HttpStatus.NOT_FOUND,"er");
        }
    }
}
