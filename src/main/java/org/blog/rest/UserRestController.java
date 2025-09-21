package org.blog.rest;

import org.blog.DTO.UserCreateDto;
import org.blog.DTO.UserDto;
import org.blog.DTO.UserProfileDto;
import org.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getRestUser(@PathVariable("id") Integer id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createRestUser(@RequestBody UserCreateDto userCreateDto) {
        UserDto user = userService.createUser(userCreateDto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<UserProfileDto> getRestUserByLogin(@PathVariable("login") String login, Authentication auth) {

        String currentUser = auth.getName();
        UserProfileDto user = userService.findByLoginDto(login, currentUser);

        return ResponseEntity.ok(user);
    }
}
