package org.blog.mapper;

import org.blog.DTO.CommentCreateDto;
import org.blog.DTO.CommentResponseDTO;
import org.blog.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "author.login", target = "author")
    CommentResponseDTO toResponseDto(Comment comment);

    @Mapping(source = "post.id", target = "postId")
    CommentCreateDto toCreateDto(Comment comment);

}
