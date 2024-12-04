package dev.teamproject;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestFilterTests {
  
  private RequestFilter requestFilter;
  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain chain;
  private StringWriter stringWriter;

  @BeforeEach
  void setUp() throws Exception {
    requestFilter = new RequestFilter();
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    chain = mock(FilterChain.class);

    stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(printWriter);
  }

  @Test
  void testExcludedPathBypassesFilter() throws Exception {
    when(request.getRequestURI()).thenReturn("/swagger-ui/index.html");
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testValidApiKeyAllowsRequest() throws Exception {
    when(request.getRequestURI()).thenReturn("/some-protected-path");
    when(request.getHeader("apiKey")).thenReturn("valid-key");
    requestFilter.setApiKey("valid-key");

    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testInvalidApiKeyReturnsUnauthorized() throws Exception {
    when(request.getRequestURI()).thenReturn("/some-protected-path");
    when(request.getHeader("apiKey")).thenReturn("invalid-key");
    requestFilter.setApiKey("valid-key");

    requestFilter.doFilterInternal(request, response, chain);

    verify(chain, never()).doFilter(request, response);
    verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // Assert the content written to response
    assertEquals("Unauthorized access: Invalid API key.", stringWriter.toString().trim());
  }

  @Test
  void testMissingApiKeyReturnsUnauthorized() throws Exception {
    when(request.getRequestURI()).thenReturn("/some-protected-path");
    when(request.getHeader("apiKey")).thenReturn(null);
    requestFilter.setApiKey("valid-key");
    requestFilter.doFilterInternal(request, response, chain);

    verify(chain, never()).doFilter(request, response);
    verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // Assert the content written to response
    assertEquals("Unauthorized access: Invalid API key.", stringWriter.toString().trim());
  }

  @Test
  void testNullPath() throws Exception {
    when(request.getRequestURI()).thenReturn(null);
    when(request.getHeader("apiKey")).thenReturn("valid-key");
    requestFilter.setApiKey("valid-key");
    requestFilter.doFilterInternal(request, response, chain);

    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testEdgeCaseExcludedPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/swagger-ui/assets.js");
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testNonExcludedPathSimilarPrefix() throws Exception {
    when(request.getRequestURI()).thenReturn("/swagger-ui-notexcluded");
    requestFilter.setApiKey("valid-key");
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, never()).doFilter(request, response);
    verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedCssPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/static/style.css");
    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedResourcesPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/resources/style.css");
    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedWebjarsPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/webjars/style.css");
    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedApidocsPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/v3/api-docs/style.css");
    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedOnlyCssPath() throws Exception {
    when(request.getRequestURI()).thenReturn("style.css");
    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedOnlyJsPath() throws Exception {
    // CHANGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEee
    when(request.getRequestURI()).thenReturn("script.js");
    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedOnlyHtmlPath() throws Exception {
    // CHANGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEee
    when(request.getRequestURI()).thenReturn("index.html");

    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedOnlyPngPath() throws Exception {
    // CHANGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEee
    when(request.getRequestURI()).thenReturn("image.png");
    // Excluded path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testExcludedFaviconPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/favicon.ico");
    // Excluded favicon path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
    
  @Test
  void testExcludedConfigPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/some-config");
    // Excluded config path should bypass the filter
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testValidNonExcludedPath() throws Exception {
    when(request.getRequestURI()).thenReturn("/protected-path");
    when(request.getHeader("apiKey")).thenReturn("valid-key");
    requestFilter.setApiKey("valid-key");
    // Valid API key and non-excluded path should allow the request
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testConcurrentRequests() throws Exception {
    // Simulating two requests with different API keys
    HttpServletRequest request1 = mock(HttpServletRequest.class);
    HttpServletRequest request2 = mock(HttpServletRequest.class);
    when(request1.getRequestURI()).thenReturn("/protected-path");
    when(request1.getHeader("apiKey")).thenReturn("key1");
    when(request2.getRequestURI()).thenReturn("/protected-path");
    when(request2.getHeader("apiKey")).thenReturn("key2");
    requestFilter.setApiKey("key1");
    // First request
    requestFilter.doFilterInternal(request1, response, chain);
    verify(chain, times(1)).doFilter(request1, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // Second request should fail
    requestFilter.doFilterInternal(request2, response, chain);
    verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Test
  void testPathWithQueryParams() throws Exception {
    when(request.getRequestURI()).thenReturn("/swagger-ui/index.html?param=value");
    requestFilter.doFilterInternal(request, response, chain);
    verify(chain, times(1)).doFilter(request, response);
    verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
