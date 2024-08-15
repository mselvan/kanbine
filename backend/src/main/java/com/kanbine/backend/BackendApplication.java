package com.kanbine.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Kanbine backend.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication
public class BackendApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        try {
            SpringApplication.run(BackendApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
