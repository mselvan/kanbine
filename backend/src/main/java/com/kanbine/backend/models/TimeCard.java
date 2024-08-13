package com.kanbine.backend.models;

import com.kanbine.backend.utils.TimeCardUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity representing a time card in the system.
 * A time card tracks the start and end time of work done by a user on an assignment.
 * The start and end times must be within the same 10-minute block.
 */
@Data
@Entity
public class TimeCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    /**
     * Validates that the start time and end time are within the same 10-minute block.
     * Throws an IllegalArgumentException if the validation fails.
     */
    @PrePersist
    @PreUpdate
    private void validateTimeCard() {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time must be provided");
        }
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (java.time.Duration.between(startTime, endTime).toMinutes() > 10) {
            throw new IllegalArgumentException("TimeCard duration cannot exceed 10 minutes");
        }
        if (!TimeCardUtils.isValidTenMinuteBlock(startTime, endTime)) {
            throw new IllegalArgumentException("Start time and end time must be within the same 10-minute block");
        }
    }
}
