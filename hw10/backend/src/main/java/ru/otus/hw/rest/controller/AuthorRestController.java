package ru.otus.hw.rest.controller;


import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.services.AuthorService;

@RestController
@AllArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;


    @GetMapping("/api/v1/authors")
    public List<AuthorDTO> getAllAuthors() {
        return authorService.findAll();
    }
}
