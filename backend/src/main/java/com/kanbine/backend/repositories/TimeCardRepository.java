package com.kanbine.backend.repositories;

import com.kanbine.backend.models.TimeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing TimeCard entities.
 * Provides basic CRUD operations inherited from JpaRepository.
 */
@Repository
public interface TimeCardRepository extends JpaRepository<TimeCard, Long> {
}
