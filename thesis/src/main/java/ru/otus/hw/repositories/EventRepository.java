package ru.otus.hw.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    @EntityGraph("event-full-graph")
    List<Event> findAll();


    @EntityGraph("event-full-graph")
    List<Event> findAllByPlaceId(long placeId);

}
