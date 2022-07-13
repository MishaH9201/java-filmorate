package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.GenreDBStorage;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.Collection;
import java.util.List;

@Service
public class MpaService {

    private final MpaDbStorage mpaDbStorage;
@Autowired
    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }


    public Collection<Mpa> findAll() {
        return mpaDbStorage.findAll();
    }

    public Mpa getMpaById(int id) {
        return mpaDbStorage.getMpaById(id);
    }
}
