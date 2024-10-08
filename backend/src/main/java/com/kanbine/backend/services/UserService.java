package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.mappers.UserMapper;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.User;
import com.kanbine.backend.models.UserAssignment;
import com.kanbine.backend.repositories.AssignmentRepository;
import com.kanbine.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 * This class provides methods to create, read, update, and delete users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper = UserMapper.INSTANCE;

    /**
     * Retrieves all users from the database.
     *
     * @return a list of UserResponse objects representing all users.
     */
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    /**
     * Saves a new user to the database.
     *
     * @param userRequest the request object containing the user details.
     * @return the saved UserResponse object.
     */
    @Transactional
    public UserResponse saveUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return an Optional containing the UserResponse object if found, otherwise empty.
     */
    public UserResponse getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toUserResponse)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Assigns an assignment to a user.
     *
     * @param userId       the ID of the user to assign the assignment to.
     * @param assignmentId the ID of the assignment to assign.
     * @return the updated UserResponse object.
     * @throws IllegalArgumentException if the user or assignment is not found.
     */
    public UserResponse assignAssignmentToUser(Long userId, Long assignmentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        user.getUserAssignments().add(new UserAssignment(user, assignment));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    /**
     * Unassigns an assignment from a user.
     *
     * @param userId       the ID of the user to unassign the assignment from.
     * @param assignmentId the ID of the assignment to unassign.
     * @return the updated UserResponse object.
     * @throws IllegalArgumentException if the user or assignment is not found.
     */
    public UserResponse unassignAssignmentFromUser(Long userId, Long assignmentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        user.getUserAssignments().removeIf(userAssignment -> userId.equals(userAssignment.getUser().getId())
                && assignmentId.equals(userAssignment.getAssignment().getId()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
