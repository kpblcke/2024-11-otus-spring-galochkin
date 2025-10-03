package ru.otus.hw.services;

import java.util.List;
import ru.otus.hw.models.Image;

public interface ImageService {
    Image getById(long id);

    List<Image> findAll();

    void deleteById(long id);

    Image save(Image image);
}
