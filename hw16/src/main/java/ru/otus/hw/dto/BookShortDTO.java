package ru.otus.hw.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookShortDTO {

    private long id;

    @NotBlank
    private String title;

    @Min(1)
    private long authorId;

    @NotEmpty
    private Set<Long> genresIds;

}
