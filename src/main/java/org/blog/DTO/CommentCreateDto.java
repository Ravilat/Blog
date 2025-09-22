package org.blog.DTO;

public record CommentCreateDto
        (
         String content,
         Integer postId) {
}
