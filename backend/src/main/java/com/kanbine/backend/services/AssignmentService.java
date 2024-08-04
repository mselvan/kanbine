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

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    private AssignmentMapper assignmentMapper = AssignmentMapper.INSTANCE;

    public List<AssignmentResponse> getAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream().map(assignmentMapper::toAssignmentResponse).collect(Collectors.toList());
    }

    public Optional<AssignmentResponse> getAssignmentById(Long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        return assignment.map(assignmentMapper::toAssignmentResponse);
    }

    public AssignmentResponse saveAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = assignmentMapper.toAssignment(assignmentRequest);
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(savedAssignment);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}
