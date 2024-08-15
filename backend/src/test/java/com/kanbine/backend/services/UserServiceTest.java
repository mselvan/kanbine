package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.dto.response.UserResponse;
import com.kanbine.backend.mappers.UserMapper;
import com.kanbine.backend.models.User;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.repositories.UserRepository;
import com.kanbine.backend.repositories.AssignmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link UserService} class.
 * These tests validate the behavior of UserService methods using mocked dependencies.
 */
class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private AutoCloseable closeable;

    /**
     * Initializes the test environment and sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setEmail("test@example.com");
    }

    /**
     * Cleans up resources after each test.
     */
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * Tests the {@link UserService#getAllUsers()} method.
     * Validates that the method returns a list of all users.
     */
    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user);
        List<UserResponse> userResponses = Arrays.asList(userResponse);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        List<UserResponse> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toUserResponse(user);
    }

    /**
     * Tests the {@link UserService#saveUser(UserRequest)} method.
     * Validates that a new user is saved correctly.
     */
    @Test
    void testSaveUser() {
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse savedUser = userService.saveUser(userRequest);

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toUser(userRequest);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    /**
     * Tests the {@link UserService#getUserById(Long)} method.
     * Validates that a user is correctly retrieved by ID.
     */
    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse foundUser = userService.getUserById(1L);

        assertEquals("test@example.com", foundUser.getEmail());

        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    /**
     * Tests the {@link UserService#deleteUser(Long)} method.
     * Validates that a user is correctly deleted by ID.
     */
    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests the {@link UserService#assignAssignmentToUser(Long, Long)} method.
     * Validates that an assignment is correctly assigned to a user.
     */
    @Test
    void testAssignAssignmentToUser() {
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setName("Test Assignment");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.assignAssignmentToUser(1L, 1L);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertTrue(user.getAssignments().contains(assignment));

        verify(userRepository, times(1)).findById(1L);
        verify(assignmentRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toUserResponse(user);
    }
}
