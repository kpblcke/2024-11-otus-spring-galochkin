package ru.otus.hw.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Analization;

public interface AnalizationRepository extends JpaRepository<Analization, Long> {

    Optional<Analization> findById(long id);

}
