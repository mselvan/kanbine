package com.kanbine.backend.repositories;

import com.kanbine.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing User entities.
 * Provides basic CRUD operations inherited from JpaRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
