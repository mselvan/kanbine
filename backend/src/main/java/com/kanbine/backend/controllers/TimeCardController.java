package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.services.TimeCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * @return a {@link ResponseEntity} containing a list of {@link TimeCardResponse} objects representing all time cards.
     */
    @GetMapping
    public ResponseEntity<List<TimeCardResponse>> getAllTimeCards() {
        List<TimeCardResponse> timeCards = timeCardService.getAllTimeCards();
        return ResponseEntity.ok(timeCards);
    }

    /**
     * Creates a new time card.
     *
     * @param timeCardRequest the {@link TimeCardRequest} object containing the time card details.
     * @return a {@link ResponseEntity} containing the created {@link TimeCardResponse} object.
     */
    @PostMapping
    public ResponseEntity<TimeCardResponse> createTimeCard(@Valid @RequestBody TimeCardRequest timeCardRequest) {
        TimeCardResponse timeCardResponse = timeCardService.saveTimeCard(timeCardRequest);
        return ResponseEntity.ok(timeCardResponse);
    }

    /**
     * Retrieves a time card by its ID.
     *
     * @param id the ID of the time card to retrieve.
     * @return a {@link ResponseEntity} containing the {@link TimeCardResponse} object if found, otherwise empty.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimeCardResponse> getTimeCardById(@PathVariable Long id) {
        TimeCardResponse timeCard = timeCardService.getTimeCardById(id);
        return ResponseEntity.ok(timeCard);
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
