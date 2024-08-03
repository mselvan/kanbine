package com.kanbine.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
        if (!isValid10MinuteBlock(startTime, endTime)) {
            throw new IllegalArgumentException("Start time and end time must be within the same 10-minute block");
        }
    }

    private boolean isValid10MinuteBlock(LocalDateTime start, LocalDateTime end) {
        long startMinute = start.getMinute();
        long endMinute = end.getMinute();
        return (startMinute / 10) == (endMinute / 10);
    }
}
