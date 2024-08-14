package com.kanbine.backend.dto.response;

import com.kanbine.backend.dto.base.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) representing a user response.
 * This DTO extends {@link UserDTO} and is used for sending user data in responses.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends UserDTO {

}
