package ru.otus.hw.dto;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO extends AbstractSubscrible {

    private long id;

    private String title;

    private String description;

    private Float price;

    private String imageLink;

    private Timestamp startTime;

    private PlaceDTO place;

    private ImageDTO image;

    private List<ThemeDTO> themes;

    private boolean subscribed;

}
