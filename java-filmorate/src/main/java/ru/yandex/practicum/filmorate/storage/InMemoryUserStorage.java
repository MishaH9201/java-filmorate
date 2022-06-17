package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.serves.InstallerId;
import ru.yandex.practicum.filmorate.serves.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        InstallerId.setId(user, users);
        Validator.validate(user);
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public void deleteUser(Integer id){
        if(users.containsKey(id)){
            users.remove(id);
        }
    }

    @Override
    public User putUser(User user){
        if(users.containsKey(user.getId())){
            InstallerId.setId(user, users);
            Validator.validate(user);
            return addUser(user);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public Collection<User> findAll(){
        return users.values();
    }

    public Map<Integer, User> getUsers(){
        return users;
    }
@Override
    public User getUserById(int id) {
        return users.get(id);
    }
}
