package ru.otus.hw.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Image;
import ru.otus.hw.repositories.ImageRepository;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final AclServiceWrapperService aclServiceWrapperService;

    private final ImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasPermission(filterObject, 'READ')")
    public Image getById(long id) {
        return imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        imageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Image save(Image image) {
        imageRepository.save(image);
        aclServiceWrapperService.createPermission(image);
        return image;
    }

}
