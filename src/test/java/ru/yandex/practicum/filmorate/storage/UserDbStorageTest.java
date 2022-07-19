package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDbStorageTest {

    private final UserStorage userStorage;

    User user = new User(1,"mail@mail.ru","nic","Олег",
            LocalDate.of(1980,11,11));

    User user1 = new User(2,"dr@mail.ru","Pet","Петя",
            LocalDate.of(1990,11,21));

    User user2 = new User(3,"l@mail.ru","Vek","Коля",
            LocalDate.of(1999,1,1));

    @Test
    @Order(1)
    public void shouldAddedAndGetByIdUser() {
        userStorage.addUser(user);
        User result = userStorage.getUserById(1);
        assertEquals(result, user);
    }

    @Test
    @Order(2)
    public void shouldUpdateUser() {
        user.setName("Саша");
        userStorage.updateUser(user);
        User result = userStorage.getUserById(1);
        assertEquals(result, user);
    }

    @Test
    @Order(3)
    public void shouldFindAllUsers() {
        user.setName("Саша");
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        Collection<User> result = userStorage.findAll();
        Collection<User> users = List.of(user,user1,user2);
        assertEquals(result, users);

    }

    @Test
    @Order(4)
    public void shouldDeleteUser() {
        userStorage.deleteUser(1);
        Collection<User> result = userStorage.findAll();
        Collection<User> users = List.of(user1,user2);
        assertEquals(result, users);

    }

}
