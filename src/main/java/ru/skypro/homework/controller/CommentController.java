package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;

@RestController("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {
    private final CommentService commentService;
    private final ImageService imageService;

    @GetMapping("/{id}/comments")
    public CommentsDTO getComments(Authentication authentication,
                                   @PathVariable int id) {
        return commentService.getComments(id);
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(Authentication authentication,
                                 @PathVariable int id,
                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return commentService.addComment(id, createOrUpdateCommentDTO);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> deleteComment(Authentication authentication,
                                                    @PathVariable int adId,
                                                    @PathVariable int commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public CommentDTO updateComment(Authentication authentication,
                                    @PathVariable int adId,
                                    @PathVariable int commentId,
                                    @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return commentService.updateComment(adId, commentId, createOrUpdateCommentDTO);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) {
        return imageService.getImage(id);
    }
}
