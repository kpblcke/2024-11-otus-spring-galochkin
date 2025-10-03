package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.otus.hw.dto.ThemeDTO;
import ru.otus.hw.models.Theme;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ThemeDtoConverter {

    ThemeDTO modelToDTO(Theme model);

    List<ThemeDTO> modelsToDTO(List<Theme> models);

}
