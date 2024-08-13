package com.kanbine.backend.seed;

import com.kanbine.backend.models.Assignment;
import com.kanbine.backend.models.TimeCard;
import com.kanbine.backend.models.User;
import com.kanbine.backend.repositories.AssignmentRepository;
import com.kanbine.backend.repositories.TimeCardRepository;
import com.kanbine.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * Seeder class for populating the database with initial test data.
 * This class runs when the application is started with the "seed" profile.
 */
@Component
@Profile("seed")
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private TimeCardRepository timeCardRepository;

    /**
     * The run method is executed at application startup.
     * It seeds the database with test data including users, assignments, and time cards.
     *
     * @param args command-line arguments passed to the application.
     * @throws Exception if there is an error during data seeding.
     */
    @Override
    public void run(String... args) throws Exception {
        // Create Users
        User user1 = new User();
        user1.setEmail("ramesh@belyf.com");
        user1.setPassword("password");

        User user2 = new User();
        user2.setEmail("suresh@belyf.com");
        user2.setPassword("password");

        userRepository.save(user1);
        userRepository.save(user2);

        // Create Assignments
        Assignment assignment1 = new Assignment();
        assignment1.setName("Technical Product Manager");
        assignment1.setDescription("TPM");
        assignment1.setHourlyRate(BigDecimal.valueOf(50));
        assignment1.setCurrency("USD");

        Assignment assignment2 = new Assignment();
        assignment2.setName("VP of Engineering");
        assignment2.setDescription("VPoE");
        assignment2.setHourlyRate(BigDecimal.valueOf(100));
        assignment2.setCurrency("USD");

        assignmentRepository.save(assignment1);
        assignmentRepository.save(assignment2);

        // Associate Users with Assignments
        user1.setAssignments(Arrays.asList(assignment1));
        user2.setAssignments(Arrays.asList(assignment2));
        userRepository.save(user1);
        userRepository.save(user2);

        // Create TimeCards for 2 hours
        createTimeCardsForDuration(user1, assignment1, 2);
        createTimeCardsForDuration(user2, assignment2, 2);
    }

    /**
     * Creates time cards for the specified duration in hours.
     * Each time card represents a 10-minute block within the specified hours.
     *
     * @param user the user to whom the time cards belong.
     * @param assignment the assignment to which the time cards are associated.
     * @param hours the number of hours for which to create time cards.
     */
    private void createTimeCardsForDuration(User user, Assignment assignment, int hours) {
        LocalDateTime startTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minusHours(hours);

        for (int i = 0; i < hours * 6; i++) { // 6 time cards per hour for 10-minute intervals
            TimeCard timeCard = new TimeCard();
            timeCard.setUser(user);
            timeCard.setAssignment(assignment);
            timeCard.setStartTime(startTime);
            timeCard.setEndTime(startTime.plusMinutes(9));

            timeCardRepository.save(timeCard);

            user.getTimeCards().add(timeCard);  // Add to user's time cards
            assignment.getTimeCards().add(timeCard);  // Add to assignment's time cards

            startTime = startTime.plusMinutes(10); // Move to the next 10-minute block
        }

        userRepository.save(user);  // Save user with updated time cards
        assignmentRepository.save(assignment);  // Save assignment with updated time cards
    }
}
