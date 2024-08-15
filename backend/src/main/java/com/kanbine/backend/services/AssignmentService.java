package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.response.AssignmentResponse;
import com.kanbine.backend.mappers.AssignmentMapper;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing assignments.
 * This class provides methods to create, read, update, and delete assignments.
 */
@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    private AssignmentMapper assignmentMapper = AssignmentMapper.INSTANCE;

    /**
     * Retrieves all assignments from the database.
     *
     * @return a list of AssignmentResponse objects representing all assignments.
     */
    public List<AssignmentResponse> getAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream().map(assignmentMapper::toAssignmentResponse).collect(Collectors.toList());
    }

    /**
     * Retrieves an assignment by its ID.
     *
     * @param id the ID of the assignment to retrieve.
     * @return an Optional containing the AssignmentResponse object if found, otherwise empty.
     */
    public AssignmentResponse getAssignmentById(Long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        return assignment.map(assignmentMapper::toAssignmentResponse)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
    }

    /**
     * Saves a new assignment to the database.
     *
     * @param assignmentRequest the request object containing the assignment details.
     * @return the saved AssignmentResponse object.
     */
    public AssignmentResponse saveAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = assignmentMapper.toAssignment(assignmentRequest);
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(savedAssignment);
    }

    /**
     * Deletes an assignment by its ID.
     *
     * @param id the ID of the assignment to delete.
     */
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}
