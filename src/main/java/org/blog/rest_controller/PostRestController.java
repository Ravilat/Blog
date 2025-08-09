package org.blog.rest_controller;

import org.blog.DTO.PostCreateDto;
import org.blog.DTO.PostResponceWithCommentsDto;
import org.blog.DTO.PostResponseDto;
import org.blog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getRestPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponceWithCommentsDto> getRestPost(@PathVariable("id") Integer id) {
        PostResponceWithCommentsDto post = postService.getResponsePostWithCommentsById(id);
        return ResponseEntity.ok(post);
    }

//    @PostMapping
//    public ResponseEntity<PostResponseDto> createRestPost(@RequestBody PostCreateDto dto, Authentication auth) {
//        PostResponseDto post = postService.createPost(dto, auth);
//        return ResponseEntity.ok(post);
//    }

}

