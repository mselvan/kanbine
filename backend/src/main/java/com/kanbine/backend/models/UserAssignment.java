package com.kanbine.backend.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the association between a user and an assignment.
 * This entity is used to manage the many-to-many relationship between users and assignments
 * with the ability to add additional attributes in the future if needed.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user_assignments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "assignment_id"})
})
public class UserAssignment {

    @EmbeddedId
    private UserAssignmentId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("assignmentId")
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    public UserAssignment(User user, Assignment assignment) {
        this.user = user;
        this.assignment = assignment;
        this.id = new UserAssignmentId(user.getId(), assignment.getId());
    }
}
