package ru.yandex.practicum.filmorate.serves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

@Service
public class UserService {

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    InMemoryUserStorage inMemoryUserStorage;

    public User addFriend(int id, int friendId){
        return null;
    }
}
