package com.kanbine.backend.services;

import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.repositories.TimeCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return timeCardRepository.save(timeCard);
    }

    public Optional<TimeCard> getTimeCardById(Long id) {
        return timeCardRepository.findById(id);
    }

    public void deleteTimeCard(Long id) {
        timeCardRepository.deleteById(id);
    }
}
