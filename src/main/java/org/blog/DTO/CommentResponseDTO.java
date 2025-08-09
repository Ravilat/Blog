package org.blog.DTO;

import java.time.LocalDateTime;

public record CommentResponseDTO
        (Integer id,
         Integer postId,
         String author,
         String content,
         LocalDateTime createDate,
         LocalDateTime updateDate) {
}
