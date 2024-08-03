package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.response.AssignmentResponse;
import com.kanbine.backend.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PostMapping
    public AssignmentResponse createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.saveAssignment(assignmentRequest);
    }

    @GetMapping("/{id}")
    public Optional<AssignmentResponse> getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
    }
}
