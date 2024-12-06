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
 * Configures the web security settings for the application.
 * Enables web security and defines custom security configurations.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private RequestFilter requestFilter;

  /**
   * Defines a security filter chain that disables CSRF protection
   * and adds a custom request filter before the UsernamePasswordAuthenticationFilter.
   *
   * @param http the HttpSecurity object used to configure the security settings
   * @return the SecurityFilterChain object with the configured security rules
   * @throws Exception if an error occurs during the configuration
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