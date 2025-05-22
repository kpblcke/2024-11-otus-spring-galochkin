package ru.otus.hw.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.BookShortDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ru.otus.hw.models.Genre;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookDtoConverter {

    BookDTO modelToDTO(Book model);

    List<BookDTO> modelsToDTO(List<Book> models);

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "genresIds", expression = "java(genresDtoToIds(model.getGenres()))")
    })
    BookShortDTO fullToShort(BookDTO model);

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "genresIds", expression = "java(genresToIds(model.getGenres()))")
    })
    BookShortDTO modelToShortDTO(Book model);

    default Set<Long> genresDtoToIds(List<GenreDTO> models) {
        return models.stream().map(GenreDTO::getId).collect(Collectors.toSet());
    }

    default Set<Long> genresToIds(List<Genre> models) {
        return models.stream().map(Genre::getId).collect(Collectors.toSet());
    }
}
