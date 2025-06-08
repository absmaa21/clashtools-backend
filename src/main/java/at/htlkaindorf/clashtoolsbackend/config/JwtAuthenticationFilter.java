package at.htlkaindorf.clashtoolsbackend.config;

import at.htlkaindorf.clashtoolsbackend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Filter for JWT-based authentication.
 * This filter intercepts incoming HTTP requests, extracts the JWT token from cookies,
 * validates it, and sets up the Spring Security context if the token is valid.
 * It extends OncePerRequestFilter to ensure it's executed once per request.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Service for JWT operations like token validation and username extraction.
     */
    private final JwtService jwtService;

    /**
     * Service for loading user details from the database.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Main filter method that processes each HTTP request.
     * This method:
     * 1. Extracts the JWT token from cookies
     * 2. Validates the token if present
     * 3. Loads user details if the token is valid
     * 4. Sets up the Spring Security context with the authenticated user
     * 5. Continues the filter chain
     *
     * @param request The HTTP request being processed
     * @param response The HTTP response
     * @param chain The filter chain for passing the request to the next filter
     * @throws ServletException If a servlet error occurs
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = Arrays.stream(cookies)
            .filter(c -> c.getName().equals("access_token"))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);

        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtService.extractUsername(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}
