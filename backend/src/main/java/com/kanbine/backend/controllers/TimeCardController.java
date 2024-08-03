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

@RestController
@RequestMapping("/api/timecards")
@Validated
public class TimeCardController {

    @Autowired
    private TimeCardService timeCardService;

    @GetMapping
    public List<TimeCardResponse> getAllTimeCards() {
        return timeCardService.getAllTimeCards();
    }

    @PostMapping
    public TimeCardResponse createTimeCard(@Valid @RequestBody TimeCardRequest timeCardRequest) {
        return timeCardService.saveTimeCard(timeCardRequest);
    }

    @GetMapping("/{id}")
    public Optional<TimeCardResponse> getTimeCardById(@PathVariable Long id) {
        return timeCardService.getTimeCardById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeCard(@PathVariable Long id) {
        timeCardService.deleteTimeCard(id);
    }
}
