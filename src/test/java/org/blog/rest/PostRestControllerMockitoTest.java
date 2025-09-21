package org.blog.rest;

import org.blog.DTO.PostResponceWithCommentsDto;
import org.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PostRestController.class)
public class PostRestControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;


    @Test
    @WithMockUser(username = "Rav")
    void getRestPostsTest() throws Exception {

        PostResponceWithCommentsDto prwcd = new PostResponceWithCommentsDto(
                1,
                "Title",
                "Content",
                "Rav",
                List.of(),
                LocalDateTime.of(2010, 11, 10, 10, 10),
                LocalDateTime.of(2011, 11, 10, 10, 10));

        Mockito.when(postService.getResponsePostWithCommentsById(1)).thenReturn(prwcd);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"));

    }


}
