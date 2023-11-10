package ru.skypro.homework.dto.comment;

import lombok.Data;

import java.util.List;
/**
 * Wrapper class for getting a list of comments
 */
@Data
public class CommentsDTO {
    private int count;
    private List<CommentDTO> results;
}

