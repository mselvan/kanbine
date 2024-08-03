package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @GetMapping("/{id}")
    public Optional<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{userId}/assignments/{assignmentId}")
    public UserResponse assignAssignmentToUser(@PathVariable Long userId, @PathVariable Long assignmentId) {
        return userService.assignAssignmentToUser(userId, assignmentId);
    }
}
