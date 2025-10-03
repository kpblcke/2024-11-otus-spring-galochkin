package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.otus.hw.dto.PlaceDTO;
import ru.otus.hw.models.Place;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaceDtoConverter {

    PlaceDTO modelToDTO(Place model);

    List<PlaceDTO> modelsToDTO(List<Place> models);

}
