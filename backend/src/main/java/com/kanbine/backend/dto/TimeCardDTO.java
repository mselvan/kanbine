package com.kanbine.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeCardDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;
    private Long assignmentId;
}
