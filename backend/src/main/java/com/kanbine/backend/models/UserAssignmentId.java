package com.kanbine.backend.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents the composite key for the {@link UserAssignment} entity,
 * combining the userId and assignmentId to uniquely identify a User-Assignment relationship.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserAssignmentId implements Serializable {
    private Long userId;
    private Long assignmentId;
}
