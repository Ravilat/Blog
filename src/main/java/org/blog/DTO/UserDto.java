package org.blog.DTO;

import java.time.LocalDateTime;

public record UserDto
        (Integer id,
         String login,
         String email,
         LocalDateTime registrationDate) {
}
