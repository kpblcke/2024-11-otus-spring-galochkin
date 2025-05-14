package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
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
import ru.otus.hw.rest.controller.CommentRestController;
import ru.otus.hw.rest.response.CommentResponse;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("Контроллер комментариев должен")
@WebMvcTest(CommentRestController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @DisplayName("Возвращать все комментарии к книге")
    @Test
    void getAllCommentByBook() throws Exception {
        AuthorDTO authorDto = new AuthorDTO(1L, "Author1 Name");
        Set<GenreDTO> genresDto = Set.of(new GenreDTO(1L, "Genre1 Name"));
        BookDTO bookDto = new BookDTO(1L, "Book1 Name", authorDto, genresDto);
        List<CommentDTO> commentsDTO = List.of(new CommentDTO(1L, "Comment1 Text"));
        CommentResponse commentResponse = new CommentResponse(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor().getFullName(), commentsDTO);

        when(bookService.getById(1L)).thenReturn(bookDto);
        when(commentService.findByBookId(1L)).thenReturn(commentsDTO);

        mvc.perform(get("/api/v1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentResponse)));
    }
}