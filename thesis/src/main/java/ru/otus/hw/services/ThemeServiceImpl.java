package ru.otus.hw.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Theme;
import ru.otus.hw.repositories.ThemeRepository;

@RequiredArgsConstructor
@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;

    @Override
    @Transactional(readOnly = true)
    public Theme getById(long id) {
        return themeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Theme not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        themeRepository.deleteById(id);
    }

}
