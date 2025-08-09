package org.blog.mapper;

import org.blog.DTO.PostResponceWithCommentsDto;
import org.blog.DTO.PostResponseDto;
import org.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface PostMapper {

    @Mapping(source = "author.login", target = "author")
    PostResponseDto toResponseDto(Post post);

    @Mapping(source = "author.login", target = "author")
    @Mapping(source = "comments", target = "comments")
    PostResponceWithCommentsDto toResponseWithCommentsDto(Post post);

}
