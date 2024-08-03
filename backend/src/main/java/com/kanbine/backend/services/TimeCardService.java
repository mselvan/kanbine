package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.mappers.TimeCardMapper;
import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.models.User;
import com.kanbine.backend.repositories.TimeCardRepository;
import com.kanbine.backend.repositories.UserRepository;
import com.kanbine.backend.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeCardService {

    @Autowired
    private TimeCardRepository timeCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    private final TimeCardMapper timeCardMapper = TimeCardMapper.INSTANCE;

    public List<TimeCardResponse> getAllTimeCards() {
        return timeCardRepository.findAll().stream()
                .map(timeCardMapper::toTimeCardResponse)
                .collect(Collectors.toList());
    }

    public Optional<TimeCardResponse> getTimeCardById(Long id) {
        return timeCardRepository.findById(id).map(timeCardMapper::toTimeCardResponse);
    }

    public TimeCardResponse saveTimeCard(TimeCardRequest timeCardRequest) {
        TimeCard timeCard = timeCardMapper.toTimeCard(timeCardRequest);
        validateTimeCard(timeCard);
        timeCard = timeCardRepository.save(timeCard);
        return timeCardMapper.toTimeCardResponse(timeCard);
    }

    public void deleteTimeCard(Long id) {
        timeCardRepository.deleteById(id);
    }

    private void validateTimeCard(TimeCard timeCard) {
        if (timeCard.getStartTime() == null || timeCard.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time must be provided");
        }
        if (!timeCard.getStartTime().isBefore(timeCard.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (ChronoUnit.MINUTES.between(timeCard.getStartTime(), timeCard.getEndTime()) > 10) {
            throw new IllegalArgumentException("TimeCard duration cannot exceed 10 minutes");
        }
        if (!isValid10MinuteBlock(timeCard.getStartTime(), timeCard.getEndTime())) {
            throw new IllegalArgumentException("Start time and end time must be within the same 10-minute block");
        }

        // Ensure user is assigned to the assignment
        User user = userRepository.findById(timeCard.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Assignment assignment = assignmentRepository.findById(timeCard.getAssignment().getId())
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        if (!assignment.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is not assigned to the assignment");
        }
    }

    private boolean isValid10MinuteBlock(LocalDateTime start, LocalDateTime end) {
        long startMinute = start.getMinute();
        long endMinute = end.getMinute();
        return (startMinute / 10) == (endMinute / 10);
    }
}
