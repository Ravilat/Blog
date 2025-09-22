package org.blog.controller;

import org.blog.DTO.PostCreateDto;
import org.blog.DTO.PostResponceWithCommentsDto;
import org.blog.DTO.PostResponseDto;
import org.blog.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String home(Model model, Authentication auth) {
        List<PostResponseDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("userName", auth.getName());
        }
        return "index";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") Integer id, Model model, Authentication auth) {

        PostResponceWithCommentsDto postWithSortedComments = postService.getResponsePostWithSortedCommentsById(id);
        model.addAttribute("post", postWithSortedComments);
        boolean isOwner = false;
        if (auth != null && auth.isAuthenticated()) {
            isOwner = postWithSortedComments.author().equals(auth.getName());
            model.addAttribute("username", auth.getName());
        }
        model.addAttribute("isPostOwner", isOwner);
        return "post";
    }

    @GetMapping("/new")
    public String showCreatePostForm(Model model, Authentication auth) {
        model.addAttribute("post", new PostCreateDto("", ""));
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("userName", auth.getName());
        }
        return "new_post";
    }

    @PostMapping("/new")
    public String createPost(@ModelAttribute("post") PostCreateDto postCreateDto, Authentication auth) {
        postService.createPost(postCreateDto, auth);
        return "redirect:/posts";
    }

    @PostMapping("/{id}/change")
    public String editPostContent(@PathVariable("id") Integer id,
                                  String newContent) {
        postService.editPostContent(id, newContent);

        return "redirect:/posts/" + id;

    }

}