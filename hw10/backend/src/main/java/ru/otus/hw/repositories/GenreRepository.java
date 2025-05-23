package ru.otus.hw.repositories;

import java.util.Collection;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Set<Genre> findAllByIdIn(Collection<Long> ids);
}
