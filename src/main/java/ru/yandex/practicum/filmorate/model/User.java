package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    @Email(message = "Wrong format email")
    private String email;
    @NotBlank(message = "Login is empty")
    private String login;
    private String name;
    @Past(message = "User's birthday in the future")
    private LocalDate birthday;


    //private Set<Integer> friends = new HashSet<>();

   // public void addFriend(int idFriend) {
     //   friends.add(idFriend);
   // }

   // public void deleteFriend(int idFriend) {
   //     friends.remove(idFriend);
  //  }
}
