package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.response.AssignmentResponse;
import com.kanbine.backend.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing assignments.
 * Provides endpoints for creating, reading, updating, and deleting assignments.
 */
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    /**
     * Retrieves a list of all assignments.
     *
     * @return a list of {@link AssignmentResponse} objects representing all assignments.
     */
    @GetMapping
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    /**
     * Creates a new assignment.
     *
     * @param assignmentRequest the {@link AssignmentRequest} object containing the assignment details.
     * @return the created {@link AssignmentResponse} object.
     */
    @PostMapping
    public AssignmentResponse createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.saveAssignment(assignmentRequest);
    }

    /**
     * Retrieves an assignment by its ID.
     *
     * @param id the ID of the assignment to retrieve.
     * @return an {@link Optional} containing the {@link AssignmentResponse} object if found, otherwise empty.
     */
    @GetMapping("/{id}")
    public Optional<AssignmentResponse> getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }

    /**
     * Deletes an assignment by its ID.
     *
     * @param id the ID of the assignment to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
    }
}
