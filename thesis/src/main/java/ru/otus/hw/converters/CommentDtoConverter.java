package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Mappings;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.models.Comment;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CommentDtoConverter {

    @Mappings({
            @Mapping(target = "eventId", source = "event.id")
    })
    CommentDTO modelToDTO(Comment model);

    @Mappings({
            @Mapping(target = "eventId", source = "event.id")
    })
    List<CommentDTO> modelsToDTO(List<Comment> models);

}
