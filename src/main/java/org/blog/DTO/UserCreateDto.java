package org.blog.DTO;

public record UserCreateDto
        (
         String login,
         String password,
         String email) {

}
