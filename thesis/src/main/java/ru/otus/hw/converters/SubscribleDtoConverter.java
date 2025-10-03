package ru.otus.hw.converters;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AbstractSubscrible;

@Component
public class SubscribleDtoConverter {

    public void modelsToDTO(List<? extends AbstractSubscrible> models, List<Long> subscribedIds) {
        models.forEach(event -> event.setSubscribed(subscribedIds.contains(event.getId())));
    }

}
