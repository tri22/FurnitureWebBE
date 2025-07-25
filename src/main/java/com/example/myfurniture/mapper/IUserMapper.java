package com.example.myfurniture.mapper;

import com.example.myfurniture.dto.response.UserResponse;
import com.example.myfurniture.dto.resquest.UserCreationReq;
import com.example.myfurniture.dto.resquest.UserUpdateReq;
import com.example.myfurniture.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    User toUser(UserCreationReq req);

    UserResponse toUserResponse(User user);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateReq req);
}
