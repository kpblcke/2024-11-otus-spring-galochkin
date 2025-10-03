package ru.otus.hw.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Override
    @EntityGraph("images-analizations-graph")
    List<Image> findAll();

    @EntityGraph("images-analizations-graph")
    Optional<Image> findById(long id);

    Optional<Image> findByName(String name);
}
