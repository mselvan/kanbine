package com.kanbine.backend.dto.request;

import com.kanbine.backend.dto.base.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserRequest extends UserDTO {
    private String password;
}
