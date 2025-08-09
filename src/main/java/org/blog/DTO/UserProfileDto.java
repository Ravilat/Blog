package org.blog.DTO;

import java.time.LocalDateTime;

public record UserProfileDto(
        String login,
        String email,
        LocalDateTime registrationDate,
        int postCount,
        int commentCount,
        boolean isOwner) {
}
