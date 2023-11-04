package ru.skypro.homework.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
/**
 * Wrapper class for receiving all information of comments
 */
@Data
public class CommentDTO {
    private int author;
    private String authorImage;
    private String authorFirstName;
    private LocalDateTime createdAt;
    private int pk;
    private String text;

}
