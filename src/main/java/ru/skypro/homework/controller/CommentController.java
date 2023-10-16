package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.service.CommentService;

import java.util.ArrayList;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {
//    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public CommentsDTO getComments() {
        CommentsDTO commentsList=new CommentsDTO(1, new ArrayList<>());
        return commentsList;
    }

    @PostMapping("/{id}/comments")
    public CreateOrUpdateCommentDTO addComment() {
        CreateOrUpdateCommentDTO newComment=new CreateOrUpdateCommentDTO();
        newComment.setText("Test1");
        return newComment;
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public CommentDTO deleteComment() {
        return null;
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public CreateOrUpdateCommentDTO updateComment() {
        CreateOrUpdateCommentDTO modifyComment=new CreateOrUpdateCommentDTO();
        modifyComment.setText("Test2");
        return modifyComment;
    }
}
