package com.kanbine.backend.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for handling time card related operations.
 * Provides methods for validating time blocks.
 */
public class TimeCardUtils {

    /**
     * Checks if the start time and end time are within the same 10-minute block.
     *
     * @param start the start time of the time block.
     * @param end the end time of the time block.
     * @return true if the times are within the same block, false otherwise.
     */
    public static boolean isValidTenMinuteBlock(LocalDateTime start, LocalDateTime end) {
        return start.truncatedTo(ChronoUnit.MINUTES).until(end.truncatedTo(ChronoUnit.MINUTES), ChronoUnit.MINUTES) < 10;
    }
}
