package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.otus.hw.dto.SubscriptionDTO;
import ru.otus.hw.models.Subscription;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionDtoConverter {

    @Mappings({
            @Mapping(target = "placeName", source = "place.name"),
            @Mapping(target = "themeName", source = "theme.name"),
            @Mapping(target = "eventTitle", source = "event.title")
    })
    SubscriptionDTO modelToDTO(Subscription model);

    List<SubscriptionDTO> modelsToDTO(List<Subscription> models);

}
