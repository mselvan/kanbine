package com.kanbine.backend.services;

import com.kanbine.backend.models.User;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.repositories.UserRepository;
import com.kanbine.backend.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User assignAssignmentToUser(Long userId, Long assignmentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        user.getAssignments().add(assignment);
        return userRepository.save(user);
    }
}
