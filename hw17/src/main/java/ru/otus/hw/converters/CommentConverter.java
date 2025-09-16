package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDTO;

@Component
public class CommentConverter {

    public String commentToString(CommentDTO comment) {
        return "Id: %d, text: %s".formatted(comment.getId(), comment.getText());
    }
}
