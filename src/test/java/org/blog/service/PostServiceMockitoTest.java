package org.blog.service;

import org.blog.DTO.PostResponseDto;
import org.blog.entity.Post;
import org.blog.mapper.PostMapper;
import org.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PostServiceMockitoTest {

        @Mock
        private PostRepository postRepository;

        @Mock
        private PostMapper postMapper;


        @InjectMocks
        private PostService postService;


        @Test
        void testGetAllPosts() {
            Post post1 = new Post();
            Post post2 = new Post();
            post1.setTitle("Title1");
            post2.setTitle("Title2");

            PostResponseDto prd1 = new PostResponseDto(
                    1,
                    "Title1",
                    "Content",
                    "Author",
                    LocalDateTime.of(2010, 11, 10, 10, 10),
                    LocalDateTime.of(2011, 11, 10, 10, 10));
            PostResponseDto prd2 = new PostResponseDto(
                    2,
                    "Title2",
                    "Content",
                    "Author",
                    LocalDateTime.of(2010, 11, 10, 10, 10),
                    LocalDateTime.of(2011, 11, 10, 10, 10));

            Mockito.when(postRepository.findAll()).thenReturn(List.of(post1, post2));
            Mockito.when(postMapper.toResponseDto(post1)).thenReturn(prd1);
            Mockito.when(postMapper.toResponseDto(post2)).thenReturn(prd2);

            List<PostResponseDto> allPosts = postService.getAllPosts();

            assertEquals(2, allPosts.size());
            assertEquals("Title1", allPosts.get(0).title());
            assertEquals("Title2", allPosts.get(1).title());
        }
}
