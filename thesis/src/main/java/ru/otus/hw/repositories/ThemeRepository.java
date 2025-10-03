package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

}
