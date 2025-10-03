package ru.otus.hw.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDTO {

    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Float price;

    private LocalDateTime startTime;

    @NotBlank
    private String imageLink;

    @Min(1)
    private long placeId;

    @NotEmpty
    private Set<Long> themesIds;

}
