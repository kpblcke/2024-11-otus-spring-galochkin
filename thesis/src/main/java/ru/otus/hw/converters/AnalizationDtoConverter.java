package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.otus.hw.dto.AnalizationDTO;
import ru.otus.hw.models.Analization;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnalizationDtoConverter {

    AnalizationDTO modelToDTO(Analization model);

    List<AnalizationDTO> modelsToDTO(List<Analization> models);

}
