package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.AssignmentDTO;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssignmentMapper {
    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    @Mapping(target = "userIds", source = "users")
    AssignmentDTO toAssignmentDTO(Assignment assignment);

    @Mapping(target = "users", ignore = true)
    Assignment toAssignment(AssignmentDTO assignmentDTO);

    default Long mapUserToId(User user) {
        return user.getId();
    }
}
