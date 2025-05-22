package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;
import ru.otus.hw.services.BookService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер книг должен")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;


    Author author = new Author(1L, "Author_1");
    List<Genre> genres = List.of(new Genre(1L, "Genre_1"));

    @WithMockUser(
            username = "user1",
            authorities = {"ROLE_USER1"}
    )
    @DisplayName("возвращать только первую книгу")
    @Test
    void shouldReturnExpectedBooksForFirstUser() throws Exception {
        AuthorDTO author1 = new AuthorDTO(1L, "Author_1");
        List<GenreDTO> genres1 = List.of(
                new GenreDTO(1L, "Genre_1"),
                new GenreDTO(2L, "Genre_2")
        );
        List<BookDTO> books = List.of(
                new BookDTO(1L, "BookTitle_1", author1, genres1)
        );

        mockMvc.perform(get("/book/all"))
                .andExpect(view().name("book/book"))
                .andExpect(model().attribute("books", books))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "user2",
            authorities = {"ROLE_USER2"}
    )
    @DisplayName("возвращать только вторую книгу")
    @Test
    void shouldReturnExpectedBooksForSecondUser() throws Exception {
        AuthorDTO author2 = new AuthorDTO(2L, "Author_2");
        List<GenreDTO> genres2 = List.of(
                new GenreDTO(3L, "Genre_3"),
                new GenreDTO(4L, "Genre_4")
        );
        List<BookDTO> books = List.of(
                new BookDTO(2L, "BookTitle_2", author2, genres2)
        );

        mockMvc.perform(get("/book/all"))
                .andExpect(view().name("book/book"))
                .andExpect(model().attribute("books", books))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "user1",
            authorities = {"ROLE_USER1"}
    )
    @Test
    void shouldReturn403ForUpdatedBook() throws Exception {
        mockMvc.perform(post("/book/edit"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("успешно обновить книгу")
    @Test
    void shouldUpdateFirstBook() throws Exception {
        BookShortDTO bookDto = new BookShortDTO(3L, "New_Title", 1L, Set.of(1L, 2L));
        mockMvc.perform(post("/book/edit").with(csrf())
                        .flashAttr("book", bookDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book/all"));
    }

    @WithMockUser(
            username = "user1",
            authorities = {"ROLE_USER1"}
    )
    @DisplayName("получить 403 для удаления книги")
    @Test
    void cantDeleteBook() throws Exception {
        mockMvc.perform(post("/book/delete/1").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("успешно удалить книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(post("/book/delete/1").with(csrf()))
                .andExpect(redirectedUrl("/book/all"));
    }
}