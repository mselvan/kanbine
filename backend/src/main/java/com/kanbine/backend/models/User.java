package com.kanbine.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Entity representing a user in the system.
 * A user has an email, password, and is associated with a list of assignments and time cards.
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<TimeCard> timeCards;

    @ManyToMany
    @JoinTable(
            name = "user_assignments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "assignment_id")
    )
    private List<Assignment> assignments;
}
