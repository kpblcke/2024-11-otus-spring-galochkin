package ru.otus.hw.services;

import java.util.List;
import ru.otus.hw.dto.GenreDTO;

public interface GenreService {
    List<GenreDTO> findAll();
}
