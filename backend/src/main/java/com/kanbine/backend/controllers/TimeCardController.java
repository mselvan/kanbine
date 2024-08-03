package com.kanbine.backend.controllers;

import com.kanbine.backend.dto.TimeCardDTO;
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
    public List<TimeCardDTO> getAllTimeCards() {
        return timeCardService.getAllTimeCards();
    }

    @PostMapping
    public TimeCardDTO createTimeCard(@RequestBody TimeCardDTO timeCardDTO) {
        return timeCardService.saveTimeCard(timeCardDTO);
    }

    @GetMapping("/{id}")
    public Optional<TimeCardDTO> getTimeCardById(@PathVariable Long id) {
        return timeCardService.getTimeCardById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeCard(@PathVariable Long id) {
        timeCardService.deleteTimeCard(id);
    }
}
