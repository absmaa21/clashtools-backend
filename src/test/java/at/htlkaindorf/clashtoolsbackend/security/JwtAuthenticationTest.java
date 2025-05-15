package at.htlkaindorf.clashtoolsbackend.security;

import at.htlkaindorf.clashtoolsbackend.config.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationTest {

    private TestJwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private UserDetails testUserDetails;

    @BeforeEach
    void setUp() {
        // Clear security context before each test
        SecurityContextHolder.clearContext();

        // Initialize the test JWT service
        jwtService = new TestJwtService();

        // Initialize the filter
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);

        // Create a test UserDetails
        testUserDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }

    @Test
    void testNoAuthorizationHeader() throws ServletException, IOException {
        // Setup
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        // Execute
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testInvalidAuthorizationHeader() throws ServletException, IOException {
        // Setup
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("InvalidHeader");

        // Execute
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testValidTokenButInvalidUser() throws ServletException, IOException {
        // Setup
        String token = "valid.token.string";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        jwtService.setExtractedUsername("testuser");
        jwtService.setValidationResult(false);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(testUserDetails);

        // Execute
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testValidTokenAndValidUser() throws ServletException, IOException {
        // Setup
        String token = "valid.token.string";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        jwtService.setExtractedUsername("testuser");
        jwtService.setValidationResult(true);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(testUserDetails);

        // Execute
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
