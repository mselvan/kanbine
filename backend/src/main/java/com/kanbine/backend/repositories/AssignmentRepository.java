package com.kanbine.backend.repositories;

import com.kanbine.backend.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Assignment entities.
 * Provides basic CRUD operations inherited from JpaRepository.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
