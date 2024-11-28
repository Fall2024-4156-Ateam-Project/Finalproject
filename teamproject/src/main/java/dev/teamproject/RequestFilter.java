package dev.teamproject;

import dev.teamproject.common.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;


  @Value("${api.SECRET_KEY}")
  private String apiKey;

  // Constructor-based injection
  // Default constructor for testing purposes
  public RequestFilter() {}
  public RequestFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  // Setter for testing
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  private boolean isExcludedPath(String path) {
    return path != null && (path.startsWith("/swagger-ui/") // Swagger UI assets
        || path.startsWith("/v3/api-docs") // OpenAPI docs
        || path.startsWith("/webjars/")   // Swagger UI dependencies
        || path.startsWith("/resources/") // Static resources like CSS/JS
        || path.startsWith("/static/")    // If assets are served from 'static'
        || path.endsWith(".css")          // Specific exclusions for stylesheets
        || path.endsWith(".js")           // Specific exclusions for JavaScript
        || path.endsWith(".html")         // Specific exclusions for HTML files
        || path.endsWith(".png")          // Exclude images
        || path.endsWith(".ico")          // Exclude favicon
        || path.endsWith("favicon.ico")
        || path.endsWith("-config"));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    String requestPath = request.getRequestURI();
    if (isExcludedPath(requestPath)) {
      chain.doFilter(request, response);
      return;
    }
    String targetKey = request.getHeader("apiKey");

    if (!apiKey.equals(targetKey)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Unauthorized access: Invalid API key.");
      return;
    }

    chain.doFilter(request, response);
  }
}