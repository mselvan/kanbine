package com.kanbine.backend.repositories;

import com.kanbine.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
