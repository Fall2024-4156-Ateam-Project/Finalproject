package dev.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Spring Boot application.
 * This class contains the main method which is used to launch the Spring application.
 * It uses SpringApplication to start the application context and run the Spring Boot
 * application.
 */

@SpringBootApplication
public class ProjectApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProjectApplication.class, args);
  }
}
