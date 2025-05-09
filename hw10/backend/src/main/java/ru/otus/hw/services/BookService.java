package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;

public interface BookService {
    Optional<BookDTO> findById(long id);

    BookDTO getById(long id);

    List<BookDTO> findAll();

    List<BookDTO> findAllByAuthorId(long authorId);

    BookDTO insert(BookShortDTO bookShortDTO);

    BookDTO update(BookShortDTO book);

    void deleteById(long id);
}
