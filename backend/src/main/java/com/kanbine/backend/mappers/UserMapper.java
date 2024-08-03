package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "assignmentIds", source = "assignments")
    UserResponse toUserResponse(User user);

    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "timeCards", ignore = true)
    User toUser(UserRequest userRequest);

    default Long mapAssignmentToId(Assignment assignment) {
        return assignment.getId();
    }
}
