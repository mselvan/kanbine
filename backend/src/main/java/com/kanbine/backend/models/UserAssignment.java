package com.kanbine.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents the association between a user and an assignment.
 * This entity is used to manage the many-to-many relationship between users and assignments
 * with the ability to add additional attributes in the future if needed.
 */
@Data
@Entity
@Table(name = "user_assignments")
public class UserAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    public UserAssignment() {}

    public UserAssignment(User user, Assignment assignment) {
        this.user = user;
        this.assignment = assignment;
    }
}
