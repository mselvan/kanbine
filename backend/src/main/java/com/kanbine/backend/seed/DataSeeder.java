package com.kanbine.backend.seed;

import com.kanbine.backend.dto.request.AssignmentRequest;
import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.request.UserRequest;
import com.kanbine.backend.services.AssignmentService;
import com.kanbine.backend.services.TimeCardService;
import com.kanbine.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Seeder class for populating the database with initial test data.
 * This class runs when the application is started with the "seed" profile.
 */
@Component
@Profile("seed")
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private TimeCardService timeCardService;

    /**
     * The run method is executed at application startup.
     * It seeds the database with test data including users, assignments, and time cards.
     *
     * @param args command-line arguments passed to the application.
     * @throws Exception if there is an error during data seeding.
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create Users
        UserRequest userRequest1 = new UserRequest();
        userRequest1.setEmail("ramesh@belyf.com");
        userRequest1.setPassword("password");

        UserRequest userRequest2 = new UserRequest();
        userRequest2.setEmail("suresh@belyf.com");
        userRequest2.setPassword("password");

        var user1 = userService.saveUser(userRequest1);
        var user2 = userService.saveUser(userRequest2);

        // Create Assignments
        AssignmentRequest assignmentRequest1 = new AssignmentRequest();
        assignmentRequest1.setName("Technical Product Manager");
        assignmentRequest1.setDescription("TPM");
        assignmentRequest1.setHourlyRate(BigDecimal.valueOf(50));
        assignmentRequest1.setCurrency("USD");

        AssignmentRequest assignmentRequest2 = new AssignmentRequest();
        assignmentRequest2.setName("VP of Engineering");
        assignmentRequest2.setDescription("VPoE");
        assignmentRequest2.setHourlyRate(BigDecimal.valueOf(100));
        assignmentRequest2.setCurrency("USD");

        var assignment1 = assignmentService.saveAssignment(assignmentRequest1);
        var assignment2 = assignmentService.saveAssignment(assignmentRequest2);

        // Associate Users with Assignments
        userService.assignAssignmentToUser(user1.getId(), assignment1.getId());
        userService.assignAssignmentToUser(user2.getId(), assignment2.getId());

        // Create TimeCards for 2 hours
        createTimeCardsForDuration(user1.getId(), assignment1.getId(), 2);
        createTimeCardsForDuration(user2.getId(), assignment2.getId(), 2);
    }

    /**
     * Creates time cards for the specified duration in hours.
     * Each time card represents a 10-minute block within the specified hours.
     *
     * @param userId the user to whom the time cards belong.
     * @param assignmentId the assignment to which the time cards are associated.
     * @param hours the number of hours for which to create time cards.
     */
    private void createTimeCardsForDuration(Long userId, Long assignmentId, int hours) {
        LocalDateTime startTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minusHours(hours);

        for (int i = 0; i < hours * 6; i++) { // 6 time cards per hour for 10-minute intervals
            var timeCardRequest = new TimeCardRequest();
            timeCardRequest.setUserId(userId);
            timeCardRequest.setAssignmentId(assignmentId);
            timeCardRequest.setStartTime(startTime);
            timeCardRequest.setEndTime(startTime.plusMinutes(9));

            timeCardService.saveTimeCard(timeCardRequest);

            startTime = startTime.plusMinutes(10); // Move to the next 10-minute block
        }
    }
}
