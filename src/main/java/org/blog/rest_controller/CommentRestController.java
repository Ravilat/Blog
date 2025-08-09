package org.blog.rest_controller;

import org.blog.DTO.CommentCreateDto;
import org.blog.DTO.CommentResponseDTO;
import org.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createRestComment(@RequestBody CommentCreateDto commentCreateDto, Authentication auth, Integer postId) {

        CommentResponseDTO comment = commentService.createComment(commentCreateDto, auth, postId);
        return ResponseEntity.ok(comment);

    }

}
