package ru.otus.hw.services;

import java.util.List;
import ru.otus.hw.models.Place;

public interface PlaceService {
    Place getById(long id);

    List<Place> findAll();

    void deleteById(long id);
}
