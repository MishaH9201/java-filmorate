package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FriendDbStorageTest {

    private final UserStorage userStorage;
    private final FriendDbStorage friendDbStorage;

    User user = new User(1,"mail@mail.ru","nic","Олег",
            LocalDate.of(1980,11,11));

    User user1 = new User(2,"dr@mail.ru","Pet","Петя",
            LocalDate.of(1990,11,21));

    User user2 = new User(3,"l@mail.ru","Vek","Коля",
            LocalDate.of(1999,1,1));

    @Test
    @Order(1)
    public void shouldAddedAndGetFriends() {
        userStorage.addUser(user);
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        friendDbStorage.addFriend(user.getId(),user1.getId());
        Collection<User> result = friendDbStorage.getAllFriends(1);
        Collection<User> users= List.of(user1);
        assertEquals(result, users);
    }

    @Test
    @Order(2)
    public void shouldGetJoinFriends() {
        friendDbStorage.addFriend(user2.getId(),user1.getId());
        Collection<User> result = friendDbStorage.getJointFriends(1,3);
        Collection<User> users= List.of(user1);
        assertEquals(result, users);
    }

    @Test
    @Order(3)
    public void shouldDeleteFriends() {
        friendDbStorage.addFriend(user2.getId(),user1.getId());
        Collection<User> users= new ArrayList<>();
        friendDbStorage.deleteFriend(1,2);
        Collection<User> result = friendDbStorage.getAllFriends(1);
        assertEquals(result, users);
    }
}
