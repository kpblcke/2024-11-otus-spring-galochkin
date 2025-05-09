package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.converters.AuthorDtoConverter;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.models.Author;
import ru.otus.hw.rest.controller.AuthorRestController;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("Контроллер авторов должен")
@WebMvcTest(AuthorRestController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorDtoConverter authorDtoConverter;


    @DisplayName("Возвращать всех авторов")
    @Test
    void getAllAuthors() throws Exception {
        List<Author> authors = List.of(new Author(1L, "Author_1"));
        List<AuthorDTO> authorDto = authorDtoConverter.modelsToDTO(authors);

        when(authorService.findAll()).thenReturn(authorDto);

        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(authorDto.toString()));
    }
}