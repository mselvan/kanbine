package com.kanbine.backend.controllers;

import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.services.TimeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/timecards")
public class TimeCardController {

    @Autowired
    private TimeCardService timeCardService;

    @GetMapping
    public List<TimeCard> getAllTimeCards() {
        return timeCardService.getAllTimeCards();
    }

    @PostMapping
    public TimeCard createTimeCard(@RequestBody TimeCard timeCard) {
        return timeCardService.saveTimeCard(timeCard);
    }

    @GetMapping("/{id}")
    public Optional<TimeCard> getTimeCardById(@PathVariable Long id) {
        return timeCardService.getTimeCardById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeCard(@PathVariable Long id) {
        timeCardService.deleteTimeCard(id);
    }
}
