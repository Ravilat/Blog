package org.blog.service;

import org.blog.DTO.UserCreateDto;
import org.blog.DTO.UserDto;
import org.blog.DTO.UserProfileDto;
import org.blog.entity.User;
import org.blog.exceptions.LoginExistingException;
import org.blog.exceptions.PasswordTooShortException;
import org.blog.mapper.UserMapper;
import org.blog.repository.CommentRepository;
import org.blog.repository.PostRepository;
import org.blog.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, CommentRepository commentRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional(readOnly = true)
    public UserProfileDto findByLoginDto(String requestedLogin, String currentLogin) {

        User user = userRepository.findByLogin(requestedLogin)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        int countPost = postRepository.countPostByAuthor(user);
        int countComment = commentRepository.countCommentByAuthor(user);

        boolean isOwner = requestedLogin.equals(currentLogin);

        UserProfileDto userProfileDto = new UserProfileDto(
                user.getLogin(),
                user.getEmail(),
                user.getRegistrationDate(),
                countPost,
                countComment,
                isOwner
        );

        return userProfileDto;
    }

    @Transactional(readOnly = true)
    public UserProfileDto findUserProfileDtoById(Integer id, String currentLogin) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        int countPost = postRepository.countPostByAuthor(user);
        int countComment = commentRepository.countCommentByAuthor(user);

        boolean isOwner = user.getLogin().equals(currentLogin);

        return new UserProfileDto(
                user.getLogin(),
                user.getEmail(),
                user.getRegistrationDate(),
                countPost,
                countComment,
                isOwner
        );
    }

    @Transactional(readOnly = true)
    public UserDto findById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return userMapper.toUserDto(optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    public UserDto createUser(UserCreateDto userCreateDto) {
        String login = userCreateDto.login().trim();
        String password = userCreateDto.password().trim();
        if (userRepository.findByLogin(login).isPresent()) {
            throw new LoginExistingException("This login exist!");
        }
        if (password.length() < 3) {
            throw new PasswordTooShortException("too short password!");
        }

        //Проверка пароля на сложность...
        //Проверка email на валидность, на повторяемость...

        User user = new User();
        user.setLogin(userCreateDto.login());
        user.setEmail(userCreateDto.email());
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserProfileDto changeEmail(UserProfileDto userProfileDto, String newEmail) {
        User user = userRepository.findByLogin(userProfileDto.login())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEmail(newEmail);
        userRepository.save(user);
        return new UserProfileDto(
                user.getLogin(),
                user.getEmail(),
                user.getRegistrationDate(),
                userProfileDto.postCount(),
                userProfileDto.commentCount(),
                userProfileDto.isOwner()
        );
    }


    public void changePassword(UserProfileDto userProfileDto, String newPassword) {
        User user = userRepository.findByLogin(userProfileDto.login())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
