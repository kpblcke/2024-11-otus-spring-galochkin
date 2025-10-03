package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.otus.hw.dto.EventDTO;
import ru.otus.hw.models.Event;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventDtoConverter {

    EventDTO modelToDTO(Event model);

    List<EventDTO> modelsToDTO(List<Event> models);

}
