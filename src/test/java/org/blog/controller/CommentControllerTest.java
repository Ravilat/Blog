package org.blog.controller;

import org.blog.AbstractMockMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest extends AbstractMockMvcTest {

    public static String URL_COMMENT_NEW = "/posts/{id}/comments/new";
    public static String URL_COMMENT = "/posts/{postId}/comment";
    public static String URL_COMMENT_EDIT = "/posts/{postId}/comment/edit/{commentId}";

    @Test
    @Transactional
    void redirectToCreateCommentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_COMMENT_NEW, 1))
                .andExpect(redirectedUrl("/posts/1/comment"));
    }

    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void showFormCreatingComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_COMMENT, 1))
                .andExpect((model().attributeExists("comment")))
                .andExpect((model().attributeExists("userName")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("new_comment"));
    }

    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void saveCommentTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(URL_COMMENT, 1)
                .param("content", "Comment"))
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void editCommentTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(URL_COMMENT_EDIT, 1, 1)
                .param("content", "newContent"))
                .andExpect(redirectedUrl("/posts/1"));

    }

}