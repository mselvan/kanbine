package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * Provides endpoints for creating, reading, updating, and deleting users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves a list of all users.
     *
     * @return a {@link ResponseEntity} containing the list of {@link UserResponse} objects representing all users.
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers();
        return ResponseEntity.ok().body(userResponseList);
    }

    /**
     * Creates a new user.
     *
     * @param userRequest the {@link UserRequest} object containing the user details.
     * @return a {@link ResponseEntity} containing the created {@link UserResponse} object.
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.saveUser(userRequest);
        return ResponseEntity.ok().body(userResponse);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return a {@link ResponseEntity} containing the {@link UserResponse} object if found, otherwise empty.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Assigns an assignment to a user.
     *
     * @param userId the ID of the user to assign the assignment to.
     * @param assignmentId the ID of the assignment to assign.
     * @return a {@link ResponseEntity} containing the updated {@link UserResponse} object.
     */
    @PostMapping("/{userId}/assignments/{assignmentId}")
    public ResponseEntity<UserResponse> assignAssignmentToUser(@PathVariable Long userId, @PathVariable Long assignmentId) {
        UserResponse userResponse = userService.assignAssignmentToUser(userId, assignmentId);
        return ResponseEntity.ok(userResponse);
    }
}
