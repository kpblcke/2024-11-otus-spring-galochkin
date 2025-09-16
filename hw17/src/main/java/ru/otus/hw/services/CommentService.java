package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;
import ru.otus.hw.dto.CommentDTO;

public interface CommentService {
    Optional<CommentDTO> findById(long id);

    List<CommentDTO> findByBookId(long bookId);

    CommentDTO insert(String text, long bookId);

    CommentDTO update(long id, String text, long bookId);

    void deleteById(long id);
}
