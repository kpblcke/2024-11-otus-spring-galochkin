package ru.otus.hw.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.EventJson;
import ru.otus.hw.dto.EventShortDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Event;
import ru.otus.hw.models.Image;
import ru.otus.hw.models.SubscriptionType;
import ru.otus.hw.models.Theme;
import ru.otus.hw.repositories.EventRepository;
import ru.otus.hw.repositories.ImageRepository;
import ru.otus.hw.repositories.PlaceRepository;
import ru.otus.hw.repositories.ThemeRepository;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.utils.UserUtils;


@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final NotificationService notificationService;

    private final EventRepository eventRepository;

    private final ThemeRepository themeRepository;

    private final UserRepository userRepository;

    private final PlaceRepository placeRepository;

    private final ImageRepository imageRepository;

    private final UserUtils userUtils;

    @Override
    @Transactional(readOnly = true)
    public Event getById(long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findAllByPlaceId(long placeId) {
        return eventRepository.findAllByPlaceId(placeId);
    }

    @Override
    public void deleteById(long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Event insert(EventShortDTO eventShortDTO) {
        Event newEvent = save(0, eventShortDTO.getTitle(), eventShortDTO.getDescription(), eventShortDTO.getPrice(),
                eventShortDTO.getStartTime(), eventShortDTO.getImageLink(), eventShortDTO.getPlaceId(),
                eventShortDTO.getThemesIds(), userUtils.getUserId());
        notificationService.notify(eventShortDTO.getThemesIds(), SubscriptionType.THEME);
        return newEvent;
    }

    @Transactional
    public Event insert(EventJson eventJson) {
        Event newEvent = save(0, eventJson.getTitle(), eventJson.getDescription(), eventJson.getPrice(),
                eventJson.getStartTime(), eventJson.getImageLink(), null, null, userUtils.getUserId());
        return newEvent;
    }

    private Event save(long id, String title, String description, Float price, LocalDateTime startTime,
            String imageLink, Long placeId, Set<Long> themesIds, long userId) {
        var place = placeId != null ? placeRepository.findById(placeId)
                .orElse(null) : null;
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %d not found".formatted(userId)));
        List<Theme> themes = new ArrayList<>();
        if (themesIds != null && !themesIds.isEmpty()) {
            themes.addAll(themeRepository.findAllById(themesIds));
            if (CollectionUtils.isEmpty(themes) || themesIds.size() != themes.size()) {
                throw new EntityNotFoundException("One or all themes with ids %s not found".formatted(themesIds));
            }
        }
        var timestamp = Timestamp.valueOf(startTime);
        Image image = imageLink != null ? imageRepository.findByName(imageLink)
                .orElse(null) : null;
        var event = new Event(id, title, description, price, imageLink, timestamp, image, place, themes);
        return eventRepository.save(event);
    }

}
