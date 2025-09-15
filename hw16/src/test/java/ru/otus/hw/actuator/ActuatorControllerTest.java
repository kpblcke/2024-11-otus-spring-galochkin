package ru.otus.hw.actuator;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Healtcheck тест")
@SpringBootTest
@AutoConfigureMockMvc
class ActuatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @DisplayName("книги есть в системе")
    @Test
    void healthIsOk() throws Exception {
        Author author = new Author(1L, "Author_1");
        List<Genre> genres = List.of(new Genre(1L, "Genre_1"));
        var books = List.of(
                new Book(1L, "Book1 Title", author, genres),
                new Book(2L, "Book2 Title", author, genres)
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @DisplayName("книжек нет в системе")
    @Test
    void healthIsDown() throws Exception {
        when(bookService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isServiceUnavailable());
    }
}