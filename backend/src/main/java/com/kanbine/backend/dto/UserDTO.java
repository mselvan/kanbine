package com.kanbine.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private List<Long> assignmentIds;
}
