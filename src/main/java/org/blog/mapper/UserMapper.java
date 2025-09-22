package org.blog.mapper;

import org.blog.DTO.UserDto;
import org.blog.DTO.UserLoginDTO;
import org.blog.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserLoginDTO toLoginDto(User user);

    UserDto toUserDto(User user);

}
