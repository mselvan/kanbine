package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.response.AssignmentResponse;
import com.kanbine.backend.mappers.AssignmentMapper;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.repositories.AssignmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link AssignmentService} class.
 * These tests validate the behavior of AssignmentService methods using mocked dependencies.
 */
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private AssignmentMapper assignmentMapper;

    @InjectMocks
    private AssignmentService assignmentService;

    private Assignment assignment;
    private AssignmentRequest assignmentRequest;
    private AssignmentResponse assignmentResponse;
    private AutoCloseable closeable;

    /**
     * Initializes the test environment and sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setName("Technical Product Manager");
        assignment.setDescription("TPM");
        assignment.setHourlyRate(BigDecimal.valueOf(50));
        assignment.setCurrency("USD");

        assignmentRequest = new AssignmentRequest();
        assignmentRequest.setName("Technical Product Manager");
        assignmentRequest.setDescription("TPM");
        assignmentRequest.setHourlyRate(BigDecimal.valueOf(50));
        assignmentRequest.setCurrency("USD");

        assignmentResponse = new AssignmentResponse();
        assignmentResponse.setId(1L);
        assignmentResponse.setName("Technical Product Manager");
        assignmentResponse.setDescription("TPM");
        assignmentResponse.setHourlyRate(BigDecimal.valueOf(50));
        assignmentResponse.setCurrency("USD");
    }

    /**
     * Cleans up resources after each test.
     */
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * Tests the {@link AssignmentService#getAllAssignments()} method.
     * Validates that the method returns a list of all assignments.
     */
    @Test
    void testGetAllAssignments() {
        List<Assignment> assignments = Arrays.asList(assignment);

        when(assignmentRepository.findAll()).thenReturn(assignments);
        when(assignmentMapper.toAssignmentResponse(assignment)).thenReturn(assignmentResponse);

        List<AssignmentResponse> result = assignmentService.getAllAssignments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(assignment.getName(), result.get(0).getName());

        verify(assignmentRepository, times(1)).findAll();
        verify(assignmentMapper, times(1)).toAssignmentResponse(assignment);
    }

    /**
     * Tests the {@link AssignmentService#saveAssignment(AssignmentRequest)} method.
     * Validates that a new assignment is saved correctly.
     */
    @Test
    void testSaveAssignment() {
        when(assignmentMapper.toAssignment(assignmentRequest)).thenReturn(assignment);
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(assignmentMapper.toAssignmentResponse(assignment)).thenReturn(assignmentResponse);

        AssignmentResponse savedAssignment = assignmentService.saveAssignment(assignmentRequest);

        assertNotNull(savedAssignment);
        assertEquals(assignment.getName(), savedAssignment.getName());

        verify(assignmentRepository, times(1)).save(assignment);
        verify(assignmentMapper, times(1)).toAssignment(assignmentRequest);
        verify(assignmentMapper, times(1)).toAssignmentResponse(assignment);
    }

    /**
     * Tests the {@link AssignmentService#getAssignmentById(Long)} method.
     * Validates that an assignment is correctly retrieved by ID.
     */
    @Test
    void testGetAssignmentById() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(assignmentMapper.toAssignmentResponse(assignment)).thenReturn(assignmentResponse);

        AssignmentResponse foundAssignment = assignmentService.getAssignmentById(1L);

        assertEquals(assignment.getName(), foundAssignment.getName());

        verify(assignmentRepository, times(1)).findById(1L);
        verify(assignmentMapper, times(1)).toAssignmentResponse(assignment);
    }

    /**
     * Tests the {@link AssignmentService#deleteAssignment(Long)} method.
     * Validates that an assignment is correctly deleted by ID.
     */
    @Test
    void testDeleteAssignment() {
        assignmentService.deleteAssignment(1L);
        verify(assignmentRepository, times(1)).deleteById(1L);
    }
}
