package com.kanbine.backend.services;

import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}
