package org.blog.service;

import org.blog.DTO.PostCreateDto;
import org.blog.DTO.PostResponceWithCommentsDto;
import org.blog.DTO.PostResponseDto;
import org.blog.entity.Post;
import org.blog.entity.User;
import org.blog.exceptions.PostNotFoundException;
import org.blog.mapper.PostMapper;
import org.blog.repository.PostRepository;
import org.blog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toResponseDto)
                .toList();
    }

    public PostResponceWithCommentsDto getResponsePostWithCommentsById(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.map(postMapper::toResponseWithCommentsDto).orElse(null);
    }


    public PostResponseDto createPost(PostCreateDto postCreateDto, Authentication auth) {
        Post post = new Post();
        post.setTitle(postCreateDto.title());
        post.setContent(postCreateDto.content());
        String login = auth.getName();
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        post.setAuthor(user);

        Post saved = postRepository.save(post);

        return postMapper.toResponseDto(saved);
    }

    public void editPostContent(Integer id, String content) {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Пост с таким id не найден!"));
        post.setContent(content);
        postRepository.save(post);

    }
}
