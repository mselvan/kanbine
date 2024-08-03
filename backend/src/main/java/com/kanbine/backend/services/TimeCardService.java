package com.kanbine.backend.services;

import com.kanbine.backend.dto.TimeCardDTO;
import com.kanbine.backend.mappers.TimeCardMapper;
import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.repositories.TimeCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeCardService {

    @Autowired
    private TimeCardRepository timeCardRepository;

    private final TimeCardMapper timeCardMapper = TimeCardMapper.INSTANCE;

    public List<TimeCardDTO> getAllTimeCards() {
        return timeCardRepository.findAll().stream()
                .map(timeCardMapper::toTimeCardDTO)
                .collect(Collectors.toList());
    }

    public Optional<TimeCardDTO> getTimeCardById(Long id) {
        return timeCardRepository.findById(id).map(timeCardMapper::toTimeCardDTO);
    }

    public TimeCardDTO saveTimeCard(TimeCardDTO timeCardDTO) {
        TimeCard timeCard = timeCardMapper.toTimeCard(timeCardDTO);
        timeCard = timeCardRepository.save(timeCard);
        return timeCardMapper.toTimeCardDTO(timeCard);
    }

    public void deleteTimeCard(Long id) {
        timeCardRepository.deleteById(id);
    }
}
