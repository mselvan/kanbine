package com.kanbine.backend.dto.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a time card.
 * This DTO is used to transfer time card data between layers of the application.
 */
@Data
public class TimeCardDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;
    private Long assignmentId;
}
