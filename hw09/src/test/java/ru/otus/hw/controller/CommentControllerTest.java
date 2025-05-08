package ru.otus.hw.controller;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер комментариев должен")
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @DisplayName("Возвращать все комментарии к книге")
    @Test
    void getAllCommentByBook() throws Exception {
        AuthorDTO author = new AuthorDTO(1L, "Author1 Name");
        GenreDTO genre = new GenreDTO(1L, "Genre1 Name");
        BookDTO book = new BookDTO(1L, "Book1 Name", author, List.of(genre));
        List<CommentDTO> comments = List.of(new CommentDTO(1L, "Comment1 Text"));

        when(bookService.findById(1l)).thenReturn(Optional.of(book));
        when(commentService.findByBookId(1l)).thenReturn(comments);

        mvc.perform(get("/comment/1").flashAttr("comments", comments))
                .andExpect(status().isOk());
    }
}