package ru.skypro.homework.dto.comment;

import lombok.Data;
/**
 * Wrapper class for changing or creating a comment
 */
@Data
public class CreateOrUpdateCommentDTO {
    private String text;
}
