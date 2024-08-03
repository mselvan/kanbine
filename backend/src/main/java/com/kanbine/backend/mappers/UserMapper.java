package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.UserDTO;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "assignmentIds", source = "assignments")
    UserDTO toUserDTO(User user);

    @Mapping(target = "assignments", ignore = true)
    User toUser(UserDTO userDTO);

    default Long mapAssignmentToId(Assignment assignment) {
        return assignment.getId();
    }
}
