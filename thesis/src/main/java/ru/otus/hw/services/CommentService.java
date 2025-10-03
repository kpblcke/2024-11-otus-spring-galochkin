package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;
import ru.otus.hw.dto.CommentDTO;

public interface CommentService {
    Optional<CommentDTO> findById(long id);

    List<CommentDTO> findByEventId(long eventId);

    CommentDTO insert(String text, long eventId, long userId);

    CommentDTO update(long id, String text, long eventId, long userId);

    void deleteById(long id);
}
