package com.kanbine.backend.dto.request;

import com.kanbine.backend.dto.base.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) representing a user creation or update request.
 * This DTO extends {@link UserDTO} and adds a password field, used during user creation or update operations.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends UserDTO {
    private String password;
}
