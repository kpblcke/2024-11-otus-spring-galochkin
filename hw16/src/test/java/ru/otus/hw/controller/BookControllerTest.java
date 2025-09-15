package ru.otus.hw.controller;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.BookDtoConverter;
import ru.otus.hw.converters.BookDtoConverterImpl;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Контроллер книг должен")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookDtoConverterImpl bookDtoConverter;

    AuthorDTO authorDto = new AuthorDTO(1L, "Author_1");
    Author author = new Author(1L, "Author_1");

    List<GenreDTO> genresDto = List.of(new GenreDTO(1L, "Genre_1"));
    List<Genre> genres = List.of(new Genre(1L, "Genre_1"));

    @DisplayName("возвращать все книги")
    @Test
    void shouldGetAllBook() throws Exception {
        var books = List.of(
                new Book(1L, "Book1 Title", author, genres),
                new Book(2L, "Book2 Title", author, genres)
        );
        var allBooks = List.of(
                new BookDTO(1L, "Book1 Title", authorDto, genresDto),
                new BookDTO(2L, "Book2 Title", authorDto, genresDto)
        );

        when(bookService.findAll()).thenReturn(books);
        when(bookDtoConverter.modelsToDTO(books)).thenReturn(allBooks);

        mockMvc.perform(get("/book/all"))
                .andExpect(model().attribute("books", allBooks))
                .andExpect(status().isOk());
    }

    @DisplayName("создавать новую книгу")
    @Test
    void shouldCreateNewBook() throws Exception {
        BookShortDTO bookDto = new BookShortDTO(0, "Title", author.getId(), Set.of(1L));
        Book book = new Book(1L, "Title", author, genres);

        when(bookService.insert(bookDto)).thenReturn(book);

        mockMvc.perform(post("/book/create").flashAttr("book", bookDto))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        BookShortDTO bookDto = new BookShortDTO(1L, "Title", author.getId(), Set.of(1L));
        Book book = new Book(1L, "Title", author, genres);

        when(bookService.update(bookDto)).thenReturn(book);

        mockMvc.perform(post("/book/edit").flashAttr("book", bookDto))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("удалять книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        doNothing().when(bookService);
        mockMvc.perform(post("/book/delete/1"))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("провалидировать поля книги")
    @Test
    void shouldValidateBookFields() throws Exception {
        BookShortDTO bookDto = new BookShortDTO(0, null, 1L, Set.of(1L));
        Book book = new Book(1L, "Book Title", author, genres);

        when(bookService.insert(bookDto)).thenReturn(book);
        mockMvc.perform(post("/book/create").flashAttr("book", bookDto))
                .andExpect(view().name("book/edit"));
    }

    @DisplayName("выбросить EntityNotFoundException при создании книги")
    @Test
    void shouldThrowEntityNotFoundExceptionOnBookCreation() throws Exception {
        BookShortDTO bookDto = new BookShortDTO(0, "New Title Book", 99L, Set.of(1L));

        when(bookService.insert(bookDto)).thenThrow(new EntityNotFoundException("Author is not found"));
        mockMvc.perform(post("/book/create").flashAttr("book", bookDto))
                .andExpect(view().name("error/entitynotfound"))
                .andExpect(model().attribute("errorMessage", "Author is not found"));
    }
}