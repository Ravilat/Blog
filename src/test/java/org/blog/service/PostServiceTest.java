package org.blog.service;

import org.blog.AbstractMockMvcTest;
import org.blog.DTO.CommentResponseDTO;
import org.blog.DTO.PostCreateDto;
import org.blog.DTO.PostResponceWithCommentsDto;
import org.blog.DTO.PostResponseDto;
import org.blog.entity.Post;
import org.blog.exceptions.PostNotFoundException;
import org.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest extends AbstractMockMvcTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Test
    void getResponsePostWithSortedCommentsByIdTest(){

        PostResponceWithCommentsDto postWithSortedComments = postService.getResponsePostWithSortedCommentsById(1);
        List<CommentResponseDTO> sortedComments = postWithSortedComments.comments();

        assertTrue(sortedComments.get(0).createDate().isBefore(sortedComments.get(1).createDate()));
        assertEquals("Rav", postWithSortedComments.author());
        assertEquals(2, sortedComments.size());
        assertEquals("Изучение Spring", postWithSortedComments.title());

    }

    @Test
    @WithMockUser(username = "Rav")
    @Transactional
    void createPostTest(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PostCreateDto postCreateDto = new PostCreateDto("TitleTest", "ContentTest");
        PostResponseDto post = postService.createPost(postCreateDto, auth);

        assertEquals("Rav", post.author());
        assertEquals("TitleTest", post.title());

    }

    @Test
    @WithMockUser(username = "Rav1")
    @Transactional
    void createPostExceptionTest(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PostCreateDto postCreateDto = new PostCreateDto("TitleTest", "ContentTest");

        assertThrows(UsernameNotFoundException.class, () -> postService.createPost(postCreateDto, auth));
    }

    @Test
    @Transactional
    void editPostContentTest(){

        postService.editPostContent(1, "SpringIntegrationTest");
        Post post = postRepository.findById(1).orElseThrow();
        assertEquals("SpringIntegrationTest", post.getContent());

    }

    @Test
    @Transactional
    void editPostContentNotFoundTest() {

        assertThrows(PostNotFoundException.class, ()->postService.editPostContent(50,"text"));

    }
}
