package org.blog.service;

import org.blog.DTO.CommentCreateDto;
import org.blog.DTO.CommentResponseDTO;
import org.blog.DTO.PostResponseDto;
import org.blog.entity.Comment;
import org.blog.entity.User;
import org.blog.exceptions.PostNotFoundException;
import org.blog.mapper.CommentMapper;
import org.blog.repository.CommentRepository;
import org.blog.repository.PostRepository;
import org.blog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, CommentMapper commentMapper, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
    }

    public List<CommentResponseDTO> getCommentsFromPost(PostResponseDto postResponseDto) {
        return commentRepository.findCommentsByPost(postResponseDto.id())
                .stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }


    public CommentResponseDTO createComment(CommentCreateDto commentCreateDto, Authentication auth, Integer postId) {

        Comment comment = new Comment();

        String login = auth.getName();
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        comment.setAuthor(user);

        comment.setPost(postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Пост не найден")));
        comment.setContent(commentCreateDto.content());

        commentRepository.save(comment);

        return commentMapper.toResponseDto(comment);
    }

    public void editComment(Integer commentId, String newCommentContent) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PostNotFoundException("нет такого комментария (service editComment"));
        comment.setContent(newCommentContent);
        commentRepository.save(comment);
    }
}
