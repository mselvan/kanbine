package com.kanbine.backend.repositories;

import com.kanbine.backend.models.TimeCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeCardRepository extends JpaRepository<TimeCard, Long> {
}
