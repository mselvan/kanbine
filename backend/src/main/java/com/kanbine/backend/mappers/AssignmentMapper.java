package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.response.AssignmentResponse;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssignmentMapper {
    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    @Mapping(target = "userIds", source = "users")
    AssignmentResponse toAssignmentResponse(Assignment assignment);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "timeCards", ignore = true)
    Assignment toAssignment(AssignmentRequest assignmentRequest);

    default Long mapUserToId(User user) {
        return user.getId();
    }
}
