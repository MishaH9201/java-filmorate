package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private final UserService userService;
	private final FilmService filmService;

	User user = new User(1,"mail@mail.ru","nic","Олег",
			LocalDate.of(1980,11,11));



	Film film = new Film(1,"Фильм","Name",
			LocalDate.of(1921, 11, 11),85,null,null);



	Film film1 = new Film(2,"Розовый фламинго","Надо смотреть",
			LocalDate.of(1972, 10, 11),85,new Mpa(5,"NC-17"),null);


	Film film2 = new Film(2,"Лак для волос","Надо смотреть",
			LocalDate.of(1988, 11, 11),85,new Mpa(5,"NC-17"),null);

	/*@Test
	public void shouldGetUser() {
		User user = userStorage.getUserById(1);
		assertEquals("nic", user.getLogin());
	}

	@Test
	public void shouldGetAllUsers() {
		Collection<User> users = userStorage.findAll();
		assertEquals(3, users.size());
	}

	@Test
	public void shouldAddUser() {
		userStorage.addUser(user);
		assertEquals("bunin@mail.ru", user.getEmail());
		userStorage.deleteUser(user.getId());
	}

	@Test
	public void shouldAddFriends() {
		userService.addFriend(1, 2);
		Collection<User> friends = userService.getAllFriends(1);
		assertEquals(2, friends.size());
	}



	@Test
	public void shouldGetFilm() {
		Film film = filmStorage.getFilmById(2);
		assertEquals("New film", film.getName());
	}

	@Test
	public void shouldGetAllFilms() {
		Collection<Film> filmList = filmStorage.findAll();
		assertEquals(3, filmList.size());
	}
*/

}

