package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.models.Author;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuthorDtoConverter {

    AuthorDTO modelToDTO(Author model);

    List<AuthorDTO> modelsToDTO(List<Author> models);

}
