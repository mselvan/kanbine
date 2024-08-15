package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.response.AssignmentResponse;
import com.kanbine.backend.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * @return a {@link ResponseEntity} containing a list of {@link AssignmentResponse} objects representing all assignments.
     */
    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {
        List<AssignmentResponse> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    /**
     * Creates a new assignment.
     *
     * @param assignmentRequest the {@link AssignmentRequest} object containing the assignment details.
     * @return a {@link ResponseEntity} containing the created {@link AssignmentResponse} object.
     */
    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        AssignmentResponse assignmentResponse = assignmentService.saveAssignment(assignmentRequest);
        return ResponseEntity.ok(assignmentResponse);
    }

    /**
     * Retrieves an assignment by its ID.
     *
     * @param id the ID of the assignment to retrieve.
     * @return a {@link ResponseEntity} containing the {@link AssignmentResponse} object if found, otherwise empty.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignmentById(@PathVariable Long id) {
        AssignmentResponse assignmentResponse = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(assignmentResponse);
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
