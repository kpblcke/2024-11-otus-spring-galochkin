package ru.otus.hw.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private long id;

    private String title;

    private AuthorDTO author;

    private Set<GenreDTO> genres;

}
