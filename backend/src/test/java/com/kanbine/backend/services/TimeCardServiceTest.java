package com.kanbine.backend.services;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.mappers.TimeCardMapper;
import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.repositories.TimeCardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link TimeCardService} class.
 * These tests validate the behavior of TimeCardService methods using mocked dependencies.
 */
class TimeCardServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(TimeCardServiceTest.class);

    @Mock
    private TimeCardRepository timeCardRepository;

    @Mock
    private TimeCardMapper timeCardMapper;

    @InjectMocks
    private TimeCardService timeCardService;

    private TimeCard timeCard;
    private TimeCardRequest timeCardRequest;
    private TimeCardResponse timeCardResponse;
    private AutoCloseable closeable;

    /**
     * Initializes the test environment and sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        timeCard = new TimeCard();
        timeCard.setId(1L);
        timeCard.setStartTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(5));
        timeCard.setEndTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(4));

        timeCardRequest = new TimeCardRequest();
        timeCardRequest.setStartTime(timeCard.getStartTime());
        timeCardRequest.setEndTime(timeCard.getEndTime());

        timeCardResponse = new TimeCardResponse();
        timeCardResponse.setId(1L);
        timeCardResponse.setStartTime(timeCard.getStartTime());
        timeCardResponse.setEndTime(timeCard.getEndTime());
    }

    /**
     * Cleans up resources after each test.
     */
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * Tests the {@link TimeCardService#getAllTimeCards()} method.
     * Validates that the method returns a list of all time cards.
     */
    @Test
    void testGetAllTimeCards() {
        List<TimeCard> timeCards = Arrays.asList(timeCard);

        when(timeCardRepository.findAll()).thenReturn(timeCards);
        when(timeCardMapper.toTimeCardResponse(timeCard)).thenReturn(timeCardResponse);

        List<TimeCardResponse> result = timeCardService.getAllTimeCards();

        logger.debug("Result: {}", result);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(timeCard.getStartTime(), result.get(0).getStartTime());

        verify(timeCardRepository, times(1)).findAll();
        verify(timeCardMapper, times(1)).toTimeCardResponse(timeCard);
    }

    /**
     * Tests the {@link TimeCardService#getTimeCardById(Long)} method.
     * Validates that a time card is correctly retrieved by ID.
     */
    @Test
    void testGetTimeCardById() {
        when(timeCardRepository.findById(1L)).thenReturn(Optional.of(timeCard));
        when(timeCardMapper.toTimeCardResponse(timeCard)).thenReturn(timeCardResponse);

        Optional<TimeCardResponse> foundTimeCard = timeCardService.getTimeCardById(1L);

        assertTrue(foundTimeCard.isPresent());
        assertEquals(timeCard.getStartTime(), foundTimeCard.get().getStartTime());

        verify(timeCardRepository, times(1)).findById(1L);
        verify(timeCardMapper, times(1)).toTimeCardResponse(timeCard);
    }

    /**
     * Tests the {@link TimeCardService#saveTimeCard(TimeCardRequest)} method.
     * Validates that a new time card is saved correctly.
     */
    @Test
    void testSaveTimeCard() {
        when(timeCardMapper.toTimeCard(timeCardRequest)).thenReturn(timeCard);
        when(timeCardRepository.save(timeCard)).thenReturn(timeCard);
        when(timeCardMapper.toTimeCardResponse(timeCard)).thenReturn(timeCardResponse);

        TimeCardResponse savedTimeCard = timeCardService.saveTimeCard(timeCardRequest);

        logger.debug("Saved TimeCard: {}", savedTimeCard);

        assertNotNull(savedTimeCard);
        assertEquals(timeCard.getStartTime(), savedTimeCard.getStartTime());

        verify(timeCardRepository, times(1)).save(timeCard);
        verify(timeCardMapper, times(1)).toTimeCard(timeCardRequest);
        verify(timeCardMapper, times(1)).toTimeCardResponse(timeCard);
    }

    /**
     * Tests the {@link TimeCardService#deleteTimeCard(Long)} method.
     * Validates that a time card is correctly deleted by ID.
     */
    @Test
    void testDeleteTimeCard() {
        timeCardService.deleteTimeCard(1L);
        verify(timeCardRepository, times(1)).deleteById(1L);
    }
}
