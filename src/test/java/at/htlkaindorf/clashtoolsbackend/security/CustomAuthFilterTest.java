package at.htlkaindorf.clashtoolsbackend.security;

import at.htlkaindorf.clashtoolsbackend.config.CustomAuthFilter;
import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import at.htlkaindorf.clashtoolsbackend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomAuthFilterTest {

    private TestJwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private PrintWriter writer;

    private CustomAuthFilter authFilter;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize the test JWT service
        jwtService = new TestJwtService();

        // Initialize the filter
        authFilter = new CustomAuthFilter(jwtService, userRepository);

        // Create a test user
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .build();

        // Setup response writer
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException e) {
            fail("Failed to setup response writer");
        }
    }

    @Test
    void testPermittedPath() throws ServletException, IOException {
        // Setup
        when(request.getRequestURI()).thenReturn("/api/auth/login");

        // Execute
        authFilter.doFilter(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        verify(request, never()).getCookies();
    }

    @Test
    void testNoCookies() throws ServletException, IOException {
        // Setup
        when(request.getRequestURI()).thenReturn("/api/user-info/current-user-id");
        when(request.getCookies()).thenReturn(null);

        // Execute
        authFilter.doFilter(request, response, filterChain);

        // Verify
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testCookiesWithoutAccessToken() throws ServletException, IOException {
        // Setup
        when(request.getRequestURI()).thenReturn("/api/user-info/current-user-id");
        Cookie[] cookies = new Cookie[] {
            new Cookie("some_cookie", "some_value")
        };
        when(request.getCookies()).thenReturn(cookies);

        // Execute
        authFilter.doFilter(request, response, filterChain);

        // Verify
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testValidTokenAndValidUser() throws ServletException, IOException {
        // Setup
        when(request.getRequestURI()).thenReturn("/api/user-info/current-user-id");
        String token = "valid.token.string";
        Cookie[] cookies = new Cookie[] {
            new Cookie("access_token", token)
        };
        when(request.getCookies()).thenReturn(cookies);

        jwtService.setExtractedUsername("testuser");
        jwtService.setValidationResult(true);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Execute
        authFilter.doFilter(request, response, filterChain);

        // Verify
        verify(request).setAttribute("currentUser", testUser);
        verify(filterChain).doFilter(request, response);
    }
}
