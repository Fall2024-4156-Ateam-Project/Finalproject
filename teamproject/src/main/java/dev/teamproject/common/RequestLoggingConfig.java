package dev.teamproject.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration class for enabling and customizing request logging.
 * This class sets up a logging filter to log details of incoming HTTP requests.
 */
@Configuration
public class RequestLoggingConfig {

  /**
   * Configures a {@link CommonsRequestLoggingFilter} to log HTTP request 
   * details such as client info, query strings, headers, and payload.
   */
  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
    loggingFilter.setIncludeClientInfo(true);
    loggingFilter.setIncludeQueryString(true);
    loggingFilter.setIncludeHeaders(true);
    loggingFilter.setIncludePayload(true);
    loggingFilter.setMaxPayloadLength(10000); // Limit on payload size to log
    return loggingFilter;
  }
}