package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.otus.hw.dto.ImageDTO;
import ru.otus.hw.models.Image;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageDtoConverter {

    ImageDTO modelToDTO(Image model);

    List<ImageDTO> modelsToDTO(List<Image> models);

}
