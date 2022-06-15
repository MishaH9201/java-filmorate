package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
private  int id;
private  String name;
    @Email(message = "Wrong format email")
private  String email;
    @NotBlank(message = "Login is empty")
private  String login;
    @Past(message = "User's birthday in the future")
private LocalDate birthday;
}
