package dev.teamproject;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        // verify(response.getWriter(), times(1)).write("Unauthorized access: Invalid API key.");

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
        // verify(response.getWriter(), times(1)).write("Unauthorized access: Invalid API key.");
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

}
