package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.converters.BookDtoConverter;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private BookDtoConverter bookDtoConverter;

    AuthorDTO author = new AuthorDTO(1L, "Author_1");
    List<GenreDTO> genres = List.of(new GenreDTO(1L, "Genre_1"));

    @DisplayName("возвращать все книги")
    @Test
    void shouldGetAllBook() throws Exception {
        var books = List.of(
                new BookDTO(1L, "Book1 Title", author, genres),
                new BookDTO(2L, "Book2 Title", author, genres)
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/book/all"))
                .andExpect(view().name("book/book"))
                .andExpect(model().attribute("books", books))
                .andExpect(status().isOk());
    }

    @DisplayName("создавать новую книгу")
    @Test
    void shouldCreateNewBook() throws Exception {
        BookShortDTO bookDto = new BookShortDTO(0, "Title", 1L, Set.of(1L));
        BookDTO book = new BookDTO(1L, "Title", author, genres);

        when(bookService.insert(bookDto)).thenReturn(book);

        mockMvc.perform(post("/book/create").flashAttr("book", bookDto))
                .andExpect(redirectedUrl("/book/all"));
    }

    @DisplayName("обновлять книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        BookShortDTO bookDto = new BookShortDTO(1L, "Title", 1L, Set.of(1L));
        BookDTO book = new BookDTO(1L, "Title", author, genres);

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
        BookDTO book = new BookDTO(1L, "Book Title", author, genres);

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