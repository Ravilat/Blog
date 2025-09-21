package org.blog.service;

import org.blog.AbstractMockMvcTest;
import org.blog.DTO.CommentCreateDto;
import org.blog.DTO.CommentResponseDTO;
import org.blog.entity.Comment;
import org.blog.exceptions.PostNotFoundException;
import org.blog.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommentServiceTest extends AbstractMockMvcTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void createCommentTest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CommentCreateDto commentCreateDto = new CommentCreateDto("New Comment", 1);
        CommentResponseDTO comment = commentService.createComment(commentCreateDto, auth, 1);

        assertEquals("Rav", comment.author());
        assertEquals("New Comment", comment.content());
        assertEquals(1, comment.postId());
    }

    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void createCommentNotFoundPostTest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CommentCreateDto commentCreateDto = new CommentCreateDto("New Comment", 1);
        assertThrows(PostNotFoundException.class, () -> commentService.createComment(commentCreateDto, auth, 100));

    }


    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void editCommentTest() {
        commentService.editComment(1, "EditedComment");
        Comment comment = commentRepository.findById(1).orElseThrow();
        assertEquals("EditedComment", comment.getContent());
    }

    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void editCommentNotFoundTest() {
        assertThrows(PostNotFoundException.class, () -> commentService.editComment(100, "text"));
    }


}
