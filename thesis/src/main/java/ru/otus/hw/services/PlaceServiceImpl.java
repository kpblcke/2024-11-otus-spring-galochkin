package ru.otus.hw.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Place;
import ru.otus.hw.repositories.PlaceRepository;


@RequiredArgsConstructor
@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public Place getById(long id) {
        return placeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Place not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        placeRepository.deleteById(id);
    }

}
