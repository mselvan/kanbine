package com.kanbine.backend.repositories;

import com.kanbine.backend.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
