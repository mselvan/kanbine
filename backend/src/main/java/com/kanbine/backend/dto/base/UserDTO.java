package com.kanbine.backend.dto.base;

import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object (DTO) representing a user.
 * This DTO is used to transfer user data between layers of the application.
 */
@Data
public class UserDTO {
    private Long id;
    private String email;
    private Set<Long> assignmentIds;
}
