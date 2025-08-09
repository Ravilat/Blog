package org.blog.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponceWithCommentsDto(
        Integer id,
        String title,
        String content,
        String author,
        List<CommentResponseDTO> comments,
        LocalDateTime createDate,
        LocalDateTime updateDate) {
}
