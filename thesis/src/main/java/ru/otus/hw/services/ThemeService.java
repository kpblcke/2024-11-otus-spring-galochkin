package ru.otus.hw.services;

import java.util.List;
import ru.otus.hw.models.Theme;

public interface ThemeService {
    Theme getById(long id);

    List<Theme> findAll();

    void deleteById(long id);
}
