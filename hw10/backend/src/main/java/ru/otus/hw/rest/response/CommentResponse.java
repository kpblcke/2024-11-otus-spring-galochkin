package ru.otus.hw.rest.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.dto.CommentDTO;


@Data
@AllArgsConstructor
public class CommentResponse {

    private long bookId;

    private String bookTitle;

    private String authorName;

    private List<CommentDTO> comments;

}
