package dev.teamproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up web security.
 * It defines the security filter chain and authentication manager
 * to manage HTTP security and authentication logic.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private RequestFilter requestFilter;

  /**
   * Configures the HTTP security settings for the application.
   * 
   * @param http the {@link HttpSecurity} object to customize the security configuration
   * 
   * @return the configured {@link SecurityFilterChain} object
   * 
   * @throws Exception if there is an error during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable());

    http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }
}