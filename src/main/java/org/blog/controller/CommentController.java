package org.blog.controller;

import org.blog.DTO.CommentCreateDto;
import org.blog.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/posts/{id}/comments/new")
    public String redirectToCreateComment(@PathVariable("id") Integer postId,
                                          Model model) {
        model.addAttribute("postId", postId);
        return "redirect:/posts/" + postId + "/comment";
    }

    @GetMapping("/posts/{postId}/comment")
    public String showFormCreatingComment(
            @PathVariable("postId") Integer postId,
            Model model,
            Authentication auth) {
        CommentCreateDto comment = new CommentCreateDto("", 0);
        model.addAttribute("comment", comment);
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("userName", auth.getName());
        }
        return "new_comment";
    }


    @PostMapping("/posts/{postId}/comment")
    public String saveComment(@PathVariable("postId") Integer postId,
                              CommentCreateDto comment,
                              Authentication auth) {

        commentService.createComment(comment, auth, postId);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable("postId") Integer postId,
                              @PathVariable("commentId") Integer commentId,
                              String newCommentContent,
                              Authentication auth){

        commentService.editComment(commentId, newCommentContent);

        return "redirect:/posts/" + postId;
    }


}
