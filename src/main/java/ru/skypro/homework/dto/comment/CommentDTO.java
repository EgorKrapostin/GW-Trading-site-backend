package ru.skypro.homework.dto.comment;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private int author;
    private String authorImage;
    private String authorFirstName;
    private Date createdAt;
    private int pk;
    private String text;

}
