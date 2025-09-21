package org.blog.controller;

import org.blog.AbstractMockMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

class PostControllerTest extends AbstractMockMvcTest {

    private static final String URL_POST = "/posts";

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_POST))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("posts"))
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(username = "Rav")
    void getPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_POST + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("isPostOwner"))
                .andExpect(view().name("post"));
    }

    @Test
    void getPostUnknownId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_POST + "/1000"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createPostGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_POST + "/new"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Rav")
    void createPostPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_POST + "/new")
                        .with(csrf())
                        .param("title", "Title")
                        .param("content", "Content"))
                .andExpect(redirectedUrl("/posts"));
    }




}