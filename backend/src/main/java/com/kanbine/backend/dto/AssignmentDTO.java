package com.kanbine.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AssignmentDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal hourlyRate;
    private String currency;
    private List<Long> userIds;
}
