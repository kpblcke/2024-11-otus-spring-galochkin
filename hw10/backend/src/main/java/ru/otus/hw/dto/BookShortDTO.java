package ru.otus.hw.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookShortDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    @NotBlank(message = "Title can not be empty")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    private String title;

    @JsonProperty("authorId")
    @NotNull(message = "Author can not be empty")
    private Long authorId;

    @JsonProperty("genres")
    @NotEmpty(message = "Book must contain at least one genre")
    private Set<Long> genresIds;

}
