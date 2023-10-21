package ru.skypro.homework.service.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.entity.Comment;

import java.util.Date;
import java.util.List;

@Component
public class CommentMapper {
    public CommentDTO commentToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(comment.getAuthor().getId());
        commentDTO.setAuthorImage(comment.getAuthor().getImage().getId());
        commentDTO.setAuthorFirstName(comment.getAuthor().getFirstName());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setPk(comment.getId());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }

    public CommentsDTO commentsToListDTO(List<CommentDTO> commentList) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(commentList.size());
        commentsDTO.setResults(commentList);
        return commentsDTO;
    }
}
