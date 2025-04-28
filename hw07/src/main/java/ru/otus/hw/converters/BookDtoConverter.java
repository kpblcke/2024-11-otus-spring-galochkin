package ru.otus.hw.converters;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.models.Book;

@Mapper(componentModel = ComponentModel.SPRING)
public interface BookDtoConverter {

    BookDTO modelToDTO(Book model);

    List<BookDTO> modelsToDTO(List<Book> models);
}
