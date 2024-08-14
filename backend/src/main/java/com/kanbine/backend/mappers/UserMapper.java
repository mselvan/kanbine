package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between User entities and DTOs.
 * Provides mapping methods to convert UserRequest to User,
 * and User to UserResponse.
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converts a User entity to a UserResponse DTO.
     * Maps the list of assignments to their corresponding IDs.
     *
     * @param user the User entity.
     * @return the corresponding UserResponse DTO.
     */
    @Mapping(target = "assignmentIds", source = "assignments")
    UserResponse toUserResponse(User user);

    /**
     * Converts a UserRequest DTO to a User entity.
     * Ignores the assignments and timeCards fields during the mapping.
     *
     * @param userRequest the DTO containing user details.
     * @return the corresponding User entity.
     */
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "timeCards", ignore = true)
    User toUser(UserRequest userRequest);

    /**
     * Maps an Assignment entity to its ID.
     * This method is used internally by MapStruct during the mapping process.
     *
     * @param assignment the Assignment entity.
     * @return the ID of the Assignment.
     */
    default Long mapAssignmentToId(Assignment assignment) {
        return assignment.getId();
    }
}
