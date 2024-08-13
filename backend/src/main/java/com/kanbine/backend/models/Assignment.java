package com.kanbine.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an assignment in the system.
 * An assignment has a name, description, hourly rate, and currency.
 * It is associated with a list of time cards and users.
 */
@Data
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private BigDecimal hourlyRate;

    @Column(nullable = false)
    private String currency = "USD";

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeCard> timeCards = new ArrayList<>();

    @ManyToMany(mappedBy = "assignments")
    private List<User> users = new ArrayList<>();
}
