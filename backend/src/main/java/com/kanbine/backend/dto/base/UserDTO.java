package com.kanbine.backend.dto.base;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    protected Long id;
    protected String email;
    protected List<Long> assignmentIds;
}
