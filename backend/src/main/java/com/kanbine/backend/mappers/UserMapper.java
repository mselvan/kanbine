package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import com.kanbine.backend.models.UserAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

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
    @Mapping(target = "assignmentIds", expression = "java(mapUserAssignmentsToAssignmentIds(user.getUserAssignments()))")
    UserResponse toUserResponse(User user);

    /**
     * Converts a UserRequest DTO to a User entity.
     * Ignores the assignments and timeCards fields during the mapping.
     *
     * @param userRequest the DTO containing user details.
     * @return the corresponding User entity.
     */
    @Mapping(target = "userAssignments", ignore = true)
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

    /**
     * Maps a list of {@link UserAssignment} entities to a list of assignment IDs.
     * <p>
     * This method extracts the assignment ID from each {@link UserAssignment} instance and returns
     * them as a list of {@code Long} values. It is used to map the relationship between
     * users and assignments in the DTO layer.
     * </p>
     *
     * @param userAssignments the list of {@link UserAssignment} entities to map.
     * @return a list of assignment IDs extracted from the provided {@link UserAssignment} entities.
     */
    default Set<Long> mapUserAssignmentsToAssignmentIds(Set<UserAssignment> userAssignments) {
        return userAssignments.stream()
                .map(userAssignment -> userAssignment.getAssignment().getId())
                .collect(Collectors.toSet());
    }

}
