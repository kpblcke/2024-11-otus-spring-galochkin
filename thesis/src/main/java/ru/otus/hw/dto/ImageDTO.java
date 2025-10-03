package ru.otus.hw.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private long id;

    private String name;

    private String filePath;

    private String mimeType;

    private List<AnalizationDTO> analization;

}
