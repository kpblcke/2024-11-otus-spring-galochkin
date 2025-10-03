package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO extends AbstractSubscrible {

    private long id;

    private String name;

    private String description;

    private String address;

    private boolean subscribed;

}
