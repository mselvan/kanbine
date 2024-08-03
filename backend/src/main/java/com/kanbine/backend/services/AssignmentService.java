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

    private final AssignmentMapper assignmentMapper = AssignmentMapper.INSTANCE;

    public List<AssignmentResponse> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(assignmentMapper::toAssignmentResponse)
                .collect(Collectors.toList());
    }

    public AssignmentResponse saveAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = assignmentMapper.toAssignment(assignmentRequest);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(assignment);
    }

    public Optional<AssignmentResponse> getAssignmentById(Long id) {
        return assignmentRepository.findById(id).map(assignmentMapper::toAssignmentResponse);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}
