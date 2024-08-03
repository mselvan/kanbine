package com.kanbine.backend.services;

import com.kanbine.backend.dto.UserDTO;
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

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserDTO);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO assignAssignmentToUser(Long userId, Long assignmentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
        user.getAssignments().add(assignment);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }
}
