package ru.otus.hw.services;

import java.util.List;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.models.Book;

public interface BookService {
    Book getById(long id);

    List<Book> findAll();

    Book insert(BookShortDTO bookShortDTO);

    Book update(BookShortDTO book);

    void deleteById(long id);
}
