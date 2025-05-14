package ru.otus.hw.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RestController
@AllArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/api/v1/genres")
    public List<GenreDTO> genre(Model model) {
        return genreService.findAll();
    }
}
