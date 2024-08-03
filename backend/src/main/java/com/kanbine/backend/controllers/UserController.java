package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.UserDTO;
import com.kanbine.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{userId}/assignments/{assignmentId}")
    public UserDTO assignAssignmentToUser(@PathVariable Long userId, @PathVariable Long assignmentId) {
        return userService.assignAssignmentToUser(userId, assignmentId);
    }
}
