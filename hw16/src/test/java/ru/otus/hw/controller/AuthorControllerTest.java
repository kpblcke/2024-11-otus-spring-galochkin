package ru.otus.hw.controller;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.services.AuthorService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер авторов должен")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @DisplayName("Возвращать всех авторов")
    @Test
    void getAllAuthors() throws Exception {
        List<AuthorDTO> authorDtos = List.of(new AuthorDTO(1L, "Author_1"));

        when(authorService.findAll()).thenReturn(authorDtos);

        mockMvc.perform(get("/author/all").flashAttr("authors", authorDtos))
                .andExpect(status().isOk());
    }
}