package com.kanbine.backend.controllers;

import com.kanbine.backend.models.Assignment;
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
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.saveAssignment(assignment);
    }

    @GetMapping("/{id}")
    public Optional<Assignment> getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
    }
}
