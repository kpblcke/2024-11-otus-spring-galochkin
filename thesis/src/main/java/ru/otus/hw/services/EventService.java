package ru.otus.hw.services;

import java.util.List;
import ru.otus.hw.dto.EventJson;
import ru.otus.hw.dto.EventShortDTO;
import ru.otus.hw.models.Event;

public interface EventService {
    Event getById(long id);

    List<Event> findAll();

    List<Event> findAllByPlaceId(long placeId);

    Event insert(EventShortDTO bookShortDTO);

    Event insert(EventJson eventJson);

    void deleteById(long id);
}
