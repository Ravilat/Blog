package org.blog.DTO;



import java.time.LocalDateTime;

public record PostResponseDto
        (Integer id,
         String title,
         String content,
         String author,
         LocalDateTime createDate,
         LocalDateTime updateDate) {
}
