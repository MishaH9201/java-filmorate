package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class User {
    private int id;
    private String name;
    @Email(message = "Wrong format email")
    private String email;
    @NotBlank(message = "Login is empty")
    private String login;
    @Past(message = "User's birthday in the future")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

    public void addFriend(int idFriend) {
        friends.add(idFriend);
    }

    public void deleteFriend(int idFriend) {
        friends.remove(idFriend);
    }
}
