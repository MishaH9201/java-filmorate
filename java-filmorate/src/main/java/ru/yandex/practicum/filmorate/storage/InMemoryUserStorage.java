package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public User deleteUser(User user){
        if(users.containsKey(user.getId())){
            users.remove(user.getId());
        }return user;
    }

    @Override
    public User putUser(User user){
        if(users.containsKey(user.getId())){
            return addUser(user);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public Collection<User> findAll(){
        return users.values();
    }
}
