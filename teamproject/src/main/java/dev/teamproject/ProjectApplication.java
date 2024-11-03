package dev.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

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

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/meetings/**") // Allow all endpoints under meetings
                .allowedOrigins("http://127.0.0.1:5500") // Allow the Live Server URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
      }
    };
  }

}
