package ru.otus.hw.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.services.GenreService;

@Controller
@AllArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre/all")
    public String genre(Model model) {
        List<GenreDTO> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genre/genre";
    }
}
