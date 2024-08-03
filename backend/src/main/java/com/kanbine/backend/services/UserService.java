package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.mappers.UserMapper;
import com.kanbine.backend.models.User;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.repositories.UserRepository;
import com.kanbine.backend.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse saveUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponse);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse assignAssignmentToUser(Long userId, Long assignmentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        user.getAssignments().add(assignment);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
