package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.models.Comment;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CommentDtoConverter {

    CommentDTO modelToDTO(Comment model);

    List<CommentDTO> modelsToDTO(List<Comment> models);

}
