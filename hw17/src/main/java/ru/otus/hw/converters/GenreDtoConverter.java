package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.models.Genre;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GenreDtoConverter {

    GenreDTO modelToDTO(Genre model);

    List<GenreDTO> modelsToDTO(List<Genre> models);

}
