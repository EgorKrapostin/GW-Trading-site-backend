package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.service.CommentService;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> getComments(Authentication authentication,
                                                   @PathVariable int id) {
        return commentService.getComments(authentication, id);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CreateOrUpdateCommentDTO> addComment(Authentication authentication,
                                                 @PathVariable int id,
                                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return commentService.addComment(authentication, id, createOrUpdateCommentDTO);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> deleteComment(Authentication authentication,
                                           @PathVariable int adId,
                                           @PathVariable int commentId) {
        return commentService.deleteComment(authentication, adId, commentId);
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CreateOrUpdateCommentDTO> updateComment(Authentication authentication,
                                           @PathVariable int adId,
                                           @PathVariable int commentId,
                                           @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return commentService.updateComment(authentication, adId, commentId, createOrUpdateCommentDTO);
    }
}
