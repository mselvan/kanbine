package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.services.TimeCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing time cards.
 * Provides endpoints for creating, reading, updating, and deleting time cards.
 */
@RestController
@RequestMapping("/api/timecards")
@Validated
public class TimeCardController {

    @Autowired
    private TimeCardService timeCardService;

    /**
     * Retrieves a list of all time cards.
     *
     * @return a list of {@link TimeCardResponse} objects representing all time cards.
     */
    @GetMapping
    public List<TimeCardResponse> getAllTimeCards() {
        return timeCardService.getAllTimeCards();
    }

    /**
     * Creates a new time card.
     *
     * @param timeCardRequest the {@link TimeCardRequest} object containing the time card details.
     * @return the created {@link TimeCardResponse} object.
     */
    @PostMapping
    public TimeCardResponse createTimeCard(@Valid @RequestBody TimeCardRequest timeCardRequest) {
        return timeCardService.saveTimeCard(timeCardRequest);
    }

    /**
     * Retrieves a time card by its ID.
     *
     * @param id the ID of the time card to retrieve.
     * @return an {@link Optional} containing the {@link TimeCardResponse} object if found, otherwise empty.
     */
    @GetMapping("/{id}")
    public Optional<TimeCardResponse> getTimeCardById(@PathVariable Long id) {
        return timeCardService.getTimeCardById(id);
    }

    /**
     * Deletes a time card by its ID.
     *
     * @param id the ID of the time card to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteTimeCard(@PathVariable Long id) {
        timeCardService.deleteTimeCard(id);
    }
}
