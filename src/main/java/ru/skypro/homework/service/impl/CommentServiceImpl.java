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
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.mapper.CommentMapper;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;

    @Override
    public CommentsDTO getComments(int id) {
        List<CommentDTO> comments = commentRepository.getCommentsByAd_Id(id)
                .stream()
                .map(commentMapper::commentToDTO)
                .collect(Collectors.toList());
        if (comments.isEmpty())
            throw new CommentNotFoundException();
        else
            return commentMapper.commentsToListDTO(comments);
    }

    @Override
    public CommentDTO addComment(int id, CreateOrUpdateCommentDTO comment) {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        Comment newComment = new Comment();
        newComment.setAuthor(ad.getAuthor());
        newComment.setAd(ad);
        newComment.setCreatedAt(newComment.getCreatedAt());
        newComment.setText(comment.getText());
        commentRepository.save(newComment);
        return commentMapper.commentToDTO(newComment);
    }

    @Override
    public void deleteComment(int adId, int commentId) {
        if (commentId > getComments(adId).getResults().size()
                || commentId <= 0) {
            throw new CommentNotFoundException();
        }
        commentRepository.deleteById(commentId);
    }


    @Override
    public CommentDTO updateComment(int adId, int commentId, CreateOrUpdateCommentDTO comment) {
        if (commentId > getComments(adId).getResults().size() || commentId <= 0) {
            throw new CommentNotFoundException();
        }
        Comment updateComment=commentRepository.findById(commentId).orElseThrow();
        updateComment.setText(comment.getText());
        commentRepository.save(updateComment);
        return commentMapper.commentToDTO(updateComment);
    }
}
