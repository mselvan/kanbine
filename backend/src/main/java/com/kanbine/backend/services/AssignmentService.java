package com.kanbine.backend.services;

import com.kanbine.backend.dto.AssignmentDTO;
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

    public List<AssignmentDTO> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(assignmentMapper::toAssignmentDTO)
                .collect(Collectors.toList());
    }

    public AssignmentDTO saveAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentMapper.toAssignment(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentDTO(assignment);
    }

    public Optional<AssignmentDTO> getAssignmentById(Long id) {
        return assignmentRepository.findById(id).map(assignmentMapper::toAssignmentDTO);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}
