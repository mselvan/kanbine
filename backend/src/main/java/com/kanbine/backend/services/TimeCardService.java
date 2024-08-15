package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.mappers.TimeCardMapper;
import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.repositories.AssignmentRepository;
import com.kanbine.backend.repositories.TimeCardRepository;
import com.kanbine.backend.repositories.UserRepository;
import com.kanbine.backend.utils.TimeCardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing time cards.
 * This class provides methods to create, read, update, and delete time cards.
 */
@Service
public class TimeCardService {

    @Autowired
    private TimeCardRepository timeCardRepository;

    private TimeCardMapper timeCardMapper = TimeCardMapper.INSTANCE;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    /**
     * Retrieves all time cards from the database.
     *
     * @return a list of TimeCardResponse objects representing all time cards.
     */
    public List<TimeCardResponse> getAllTimeCards() {
        List<TimeCard> timeCards = timeCardRepository.findAll();
        return timeCards.stream().map(timeCardMapper::toTimeCardResponse).collect(Collectors.toList());
    }

    /**
     * Retrieves a time card by its ID.
     *
     * @param id the ID of the time card to retrieve.
     * @return an Optional containing the TimeCardResponse object if found, otherwise empty.
     */
    public TimeCardResponse getTimeCardById(Long id) {
        Optional<TimeCard> timeCard = timeCardRepository.findById(id);
        return timeCard.map(timeCardMapper::toTimeCardResponse)
                .orElseThrow(() -> new IllegalArgumentException("Timecard not found"));
    }

    /**
     * Saves a new time card to the database.
     *
     * @param timeCardRequest the request object containing the time card details.
     * @return the saved TimeCardResponse object.
     */
    public TimeCardResponse saveTimeCard(TimeCardRequest timeCardRequest) {
        validateTimeCard(timeCardRequest);
        TimeCard timeCard = timeCardMapper.toTimeCard(timeCardRequest);
        timeCard.setUser(userRepository.findById(timeCardRequest.getUserId()).get());
        timeCard.setAssignment(assignmentRepository.findById(timeCardRequest.getAssignmentId()).get());
        TimeCard savedTimeCard = timeCardRepository.save(timeCard);
        return timeCardMapper.toTimeCardResponse(savedTimeCard);
    }

    /**
     * Deletes a time card by its ID.
     *
     * @param id the ID of the time card to delete.
     */
    public void deleteTimeCard(Long id) {
        timeCardRepository.deleteById(id);
    }

    /**
     * Validates the time card request.
     * Ensures that the start time and end time are within the same 10-minute block.
     *
     * @param timeCardRequest the request object containing the time card details.
     * @throws IllegalArgumentException if the time card is not valid.
     */
    private void validateTimeCard(TimeCardRequest timeCardRequest) {
        if (!TimeCardUtils.isValidTenMinuteBlock(timeCardRequest.getStartTime(), timeCardRequest.getEndTime())) {
            throw new IllegalArgumentException("Start time and end time must be within the same 10-minute block");
        }
    }
}
