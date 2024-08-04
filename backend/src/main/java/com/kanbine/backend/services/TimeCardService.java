package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.mappers.TimeCardMapper;
import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.repositories.TimeCardRepository;
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
    private TimeCardMapper timeCardMapper;

    public List<TimeCardResponse> getAllTimeCards() {
        List<TimeCard> timeCards = timeCardRepository.findAll();
        return timeCards.stream().map(timeCardMapper::toTimeCardResponse).collect(Collectors.toList());
    }

    public Optional<TimeCardResponse> getTimeCardById(Long id) {
        Optional<TimeCard> timeCard = timeCardRepository.findById(id);
        return timeCard.map(timeCardMapper::toTimeCardResponse);
    }

    public TimeCardResponse saveTimeCard(TimeCardRequest timeCardRequest) {
        validateTimeCard(timeCardRequest);
        TimeCard timeCard = timeCardMapper.toTimeCard(timeCardRequest);
        TimeCard savedTimeCard = timeCardRepository.save(timeCard);
        return timeCardMapper.toTimeCardResponse(savedTimeCard);
    }

    public void deleteTimeCard(Long id) {
        timeCardRepository.deleteById(id);
    }

    private void validateTimeCard(TimeCardRequest timeCardRequest) {
        if (!isValidTimeBlock(timeCardRequest.getStartTime(), timeCardRequest.getEndTime())) {
            throw new IllegalArgumentException("Start time and end time must be within the same 10-minute block");
        }
    }

    private boolean isValidTimeBlock(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.truncatedTo(ChronoUnit.MINUTES).until(endTime.truncatedTo(ChronoUnit.MINUTES), ChronoUnit.MINUTES) < 10;
    }
}
