package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.Collection;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private final UserService userService;
	private final FilmService filmService;

/*	User user = new User(
			"bunin@mail.ru",
			"qwerty",
			"Иван",
			LocalDate.of(1970, 10, 22))


	Film film = Film.builder()
			.id(1L)
			.name("Lion King")
			.description("Meow Simba meow")
			.releaseDate(LocalDate.of(1994, 3, 3))
			.duration(85)
			.build();

	Film film1 = Film.builder()
			.id(2L)
			.name("The Thing")
			.description("A research team in Antarctica is hunted by a shape-shifting alien.")
			.releaseDate(LocalDate.of(1982, 3, 3))
			.duration(95)
			.build();

	Film film2 = Film.builder()
			.id(3L)
			.name("Matrix")
			.description("Knock knock, Neo")
			.releaseDate(LocalDate.of(1999, 3, 3))
			.duration(150)
			.build();

	@Test
	public void shouldGetUser() {
		User user = userStorage.findById(1L);
		assertEquals("dolore", user.getLogin());
	}

	@Test
	public void shouldGetAllUsers() {
		Collection<User> users = userStorage.getAll();
		assertEquals(3, users.size());
	}

	@Test
	public void shouldCreateUser() {
		userStorage.create(user);
		assertEquals("bunin@mail.ru", user.getEmail());
		userStorage.delete(user);
	}

	@Test
	public void shouldDeleteUser() {
		userStorage.create(user);
		userStorage.delete(user);
		Collection<User> allUsers = userStorage.getAllUsers();
		assertEquals(3, allUsers.size());
	}

	@Test
	public void shouldUpdateUser() {
		userStorage.create(user);
		user.setEmail("wownewemail@happy.com");
		userStorage.update(user);
		assertEquals("wownewemail@happy.com", user.getEmail());
		userStorage.delete(user);
	}

	@Test
	public void shouldGetFriends() {
		List<User> friendList = userService.getFriends(1L);
		assertEquals(3, friendList.get(0).getId());
	}

	@Test
	public void shouldAddFriends() {
		userService.addFriend(1L, 2L);
		List<User> friendList = userService.getFriends(1L);
		assertEquals(2, friendList.size());
	}

	@Test
	public void shouldRemoveFriends() {
		userService.deleteFriend(2L, 3L);
		List<User> friendList = userService.getFriends(2L);
		assertTrue(friendList.isEmpty());
	}

	@Test
	public void shouldGetFilm() {
		Film film = filmStorage.findById(2L);
		assertEquals("New film", film.getName());
	}

	@Test
	public void shouldGetAllFilms() {
		Collection<Film> filmList = filmStorage.getAllFilms();
		assertEquals(3, filmList.size());
	}

	@Test
	public void shouldCreateFilm() {
		Film newFilm = Film.builder()
				.id(10L)
				.name("newFilm")
				.description("newFilmDescription")
				.releaseDate(LocalDate.of(1972, 1, 14))
				.mpa(Mpa.builder().id(3).name("PG-13").build())
				.duration(85)
				.build();

		filmService.create(newFilm);
		assertTrue(filmService.getAllFilms().contains(newFilm));
	}

	@Test
	public void shouldDeleteFilm() {
		Film filmToDelete = Film.builder()
				.id(11L)
				.name("filmToDelete")
				.description("filmToDeleteDescription")
				.releaseDate(LocalDate.of(1973, 2, 13))
				.mpa(Mpa.builder().id(3).name("PG-13").build())
				.duration(85)
				.build();

		filmService.create(filmToDelete);
		filmStorage.delete(filmToDelete);
		assertFalse(filmService.getAllFilms().contains(filmToDelete));
	}
*/
}
