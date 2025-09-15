package ru.otus.hw.service;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.BookDtoConverterImpl;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.BookServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис для работы с книгами ")
@DataJpaTest
@Import({BookDtoConverterImpl.class, BookConverter.class, AuthorConverter.class,
        GenreConverter.class, BookServiceImpl.class})
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceTest {

    @Autowired
    BookService bookService;

    @DisplayName("должен найти книгу по id")
    @Test
    void shouldFindBookById() {
        var actualBook = bookService.getById(1L);

        assertThat(actualBook.getId()).isEqualTo(1L);
        assertThat(actualBook.getTitle()).isEqualTo("BookTitle_1");
        assertThat(actualBook.getAuthor()).isNotNull();
        assertThat(actualBook.getAuthor().getId()).isEqualTo(1L);
        assertThat(actualBook.getGenres()).isNotEmpty().hasSize(2);
    }

    @DisplayName("должен найти все книги")
    @Test
    void shouldFindAllBooks() {
        var actualBooks = bookService.findAll();

        assertThat(actualBooks)
                .isNotEmpty()
                .hasSize(3)
                .allMatch(b -> b.getTitle() != null)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenres() != null && !b.getGenres().isEmpty());
    }

    @DisplayName("должен добавлять новую книгу")
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void shouldAddNewBook() {
        BookShortDTO bookShortDTO = new BookShortDTO(0, "Test_Title_book", 1L, Set.of(1L));
        var insertedBook = bookService.insert(bookShortDTO);
        var dbBook = bookService.getById(insertedBook.getId());

        assertThat(insertedBook)
                .usingRecursiveComparison()
                .isEqualTo(dbBook);
    }

    @DisplayName("должен выбросить EntityNotFoundException при создании")
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void shouldThrowEntityNotFoundExceptionAtInsert() {
        BookShortDTO bookShortDTO = new BookShortDTO(0, "Test_Title_book", 1111, Set.of(1L));
        assertThrows(EntityNotFoundException.class, () -> bookService.insert(bookShortDTO));
    }

    @DisplayName("должен обновлять книгу")
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void shouldUpdateBook() {
        var oldBook = bookService.getById(1L);
        var genreIds = oldBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        BookShortDTO bookShortDTO = new BookShortDTO(oldBook.getId(), "New title", oldBook.getAuthor().getId(), genreIds);
        var updatedBook = bookService.update(bookShortDTO);
        var dbBook = bookService.getById(1L);

        assertThat(updatedBook)
                .usingRecursiveComparison()
                .isEqualTo(dbBook);
    }

    @DisplayName("должен выбросить EntityNotFoundException при обновлении")
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    void shouldThrowEntityNotFoundExceptionAtUpdate() {
        var oldBook = bookService.getById(1L);
        var genreIds = oldBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        BookShortDTO bookShortDTO = new BookShortDTO(oldBook.getId(), "New title", 0, genreIds);
        assertThrows(EntityNotFoundException.class,
                () -> bookService.update(bookShortDTO));
    }

}