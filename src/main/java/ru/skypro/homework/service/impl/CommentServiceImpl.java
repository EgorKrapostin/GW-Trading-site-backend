package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exeption.AdNotFoundException;
import ru.skypro.homework.exeption.CommentNotFoundException;
import ru.skypro.homework.exeption.ForbiddenException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.mapper.CommentMapper;


import java.util.List;
import java.util.stream.Collectors;
/**
 * A class with CRUD methods for comment
 */

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserService userService;
    private final AdServiceImpl adService;
    /**
     * Method for getting comments
     */
    @Override
    public CommentsDTO getComments(int adId) {
        List<CommentDTO> comments = commentRepository.getCommentsByAd_Id(adId)
                .stream()
                .map(commentMapper::commentToDTO)
                .collect(Collectors.toList());
        return commentMapper.commentsToListDTO(comments);
    }
    /**
     * Method for adding a comment
     */
    @Override
    public CommentDTO addComment(int adId, CreateOrUpdateCommentDTO comment) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Comment newComment = new Comment();
        newComment.setAuthor(userService.findAuthUser().orElseThrow(ForbiddenException::new));
        newComment.setAd(ad);
        newComment.setCreatedAt(newComment.getCreatedAt());
        newComment.setText(comment.getText());
        commentRepository.save(newComment);
        return commentMapper.commentToDTO(newComment);
    }
    /**
     * Method for delete a comment
     */
    @Override
    public void deleteComment(int adId, int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (adId != (comment.getAd().getId())) {
            throw new CommentNotFoundException();
        } else {
            if (adService.checkAccess(adId)) {
                commentRepository.delete(comment);
            } else {
                throw new ForbiddenException();
            }
        }
    }
    /**
     * Method for changing a comment
     */
    @Override
    public CommentDTO updateComment(int adId, int commentId, CreateOrUpdateCommentDTO comment) {
        Comment updateComment = commentRepository.findById(commentId).orElseThrow();
        if (adId != (updateComment.getAd().getId())) {
            throw new CommentNotFoundException();
        } else {
            if (adService.checkAccess(adId)) {
                updateComment.setText(comment.getText());
                commentRepository.save(updateComment);
                return commentMapper.commentToDTO(updateComment);
            } else {
                throw new ForbiddenException();
            }
        }
    }
}
