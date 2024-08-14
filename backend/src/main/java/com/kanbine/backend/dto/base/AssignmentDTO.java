package com.kanbine.backend.dto.base;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing an assignment.
 * This DTO is used to transfer assignment data between layers of the application.
 */
@Data
public class AssignmentDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal hourlyRate;
    private String currency;
    private List<Long> userIds;
}
