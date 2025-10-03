package ru.otus.hw.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private long id;

    private String text;

    private Timestamp createTime;

    private UserDTO author;

    private long eventId;

}
