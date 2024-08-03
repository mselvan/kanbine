package com.kanbine.backend.services;

import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.repositories.TimeCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TimeCardService {

    @Autowired
    private TimeCardRepository timeCardRepository;

    public List<TimeCard> getAllTimeCards() {
        return timeCardRepository.findAll();
    }

    public TimeCard saveTimeCard(TimeCard timeCard) {
        validateTimeCard(timeCard);
        return timeCardRepository.save(timeCard);
    }

    public Optional<TimeCard> getTimeCardById(Long id) {
        return timeCardRepository.findById(id);
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
        if (!timeCard.getAssignment().getUsers().contains(timeCard.getUser())) {
            throw new IllegalArgumentException("User is not assigned to this assignment");
        }
    }

    private boolean isValid10MinuteBlock(LocalDateTime start, LocalDateTime end) {
        long startMinute = start.getMinute();
        long endMinute = end.getMinute();
        return (startMinute / 10) == (endMinute / 10);
    }
}
