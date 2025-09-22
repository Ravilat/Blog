package org.blog.rest;

import org.blog.DTO.PostResponceWithCommentsDto;
import org.blog.DTO.PostResponseDto;
import org.blog.service.PostService;
import org.springframework.http.ResponseEntity;
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

}

