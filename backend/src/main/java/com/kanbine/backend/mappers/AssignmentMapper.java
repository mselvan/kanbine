package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.response.AssignmentResponse;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Assignment entities and DTOs.
 * Provides mapping methods to convert AssignmentRequest to Assignment,
 * and Assignment to AssignmentResponse.
 */
@Mapper
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    /**
     * Converts an Assignment entity to an AssignmentResponse DTO.
     * Maps the list of users to their corresponding IDs.
     *
     * @param assignment the Assignment entity.
     * @return the corresponding AssignmentResponse DTO.
     */
    @Mapping(target = "userIds", source = "users")
    AssignmentResponse toAssignmentResponse(Assignment assignment);

    /**
     * Converts an AssignmentRequest DTO to an Assignment entity.
     * Ignores the users and timeCards fields during the mapping.
     *
     * @param assignmentRequest the DTO containing assignment details.
     * @return the corresponding Assignment entity.
     */
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "timeCards", ignore = true)
    Assignment toAssignment(AssignmentRequest assignmentRequest);

    /**
     * Maps a User entity to its ID.
     * This method is used internally by MapStruct during the mapping process.
     *
     * @param user the User entity.
     * @return the ID of the User.
     */
    default Long mapUserToId(User user) {
        return user.getId();
    }
}
