package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;


public interface CommentService {
    ResponseEntity<CommentsDTO> getComments(Authentication authentication, int id);
    ResponseEntity<CreateOrUpdateCommentDTO> addComment(Authentication authentication, int id, CreateOrUpdateCommentDTO comment);
    ResponseEntity<CommentDTO> deleteComment(Authentication authentication, int adId, int commentId);
    ResponseEntity<CreateOrUpdateCommentDTO> updateComment(Authentication authentication, int adId, int commentId, CreateOrUpdateCommentDTO createOrUpdateComment);


}
