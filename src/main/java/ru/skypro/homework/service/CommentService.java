package ru.skypro.homework.service;

import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;


public interface CommentService {
    CommentsDTO getComments(int id);
    CommentDTO addComment(int id, CreateOrUpdateCommentDTO comment);
    void deleteComment(int adId, int commentId);
    CommentDTO updateComment(int adId, int commentId, CreateOrUpdateCommentDTO comment);


}
