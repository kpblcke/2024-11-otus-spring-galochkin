package ru.otus.hw.controller;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.services.GenreService;

@Controller
@AllArgsConstructor
@Log4j2
public class GenreController {

    private final GenreService genreService;

    private final CircuitBreaker circuitBreaker;

    @GetMapping("/genre/all")
    public String genre(Model model) {
        List<GenreDTO> genres = circuitBreaker.run(genreService::findAll,
                t -> {
                    log.error("delay call failed error:{}", t.getMessage());
                    return Collections.emptyList();
                });

        model.addAttribute("genres", genres);
        return "genre/genre";
    }
}
