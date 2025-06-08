package at.htlkaindorf.clashtoolsbackend.config;

import at.htlkaindorf.clashtoolsbackend.pojos.User;
import at.htlkaindorf.clashtoolsbackend.repositories.UserRepository;
import at.htlkaindorf.clashtoolsbackend.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Custom authentication filter that replaces Spring Security's JWT authentication.
 * This filter extracts the JWT token from cookies and validates it.
 */
@Component
@RequiredArgsConstructor
public class CustomAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Skip authentication for permitted paths
        String path = request.getRequestURI();
        if (isPermittedPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check for cookies
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            handleUnauthenticated(response);
            return;
        }

        // Extract JWT token from cookies
        String jwt = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("access_token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (jwt == null) {
            handleUnauthenticated(response);
            return;
        }

        try {
            // Validate token and extract username
            if (!jwtService.validateToken(jwt)) {
                handleUnauthenticated(response);
                return;
            }

            String username = jwtService.extractUsername(jwt);

            // Store authenticated user in request attribute
            User user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user == null) {
                handleUnauthenticated(response);
                return;
            }

            // Set authenticated user in request attribute
            request.setAttribute("currentUser", user);

            // Continue with the filter chain
            chain.doFilter(request, response);

        } catch (Exception e) {
            handleUnauthenticated(response);
        }
    }

    private boolean isPermittedPath(String path) {
        return path.startsWith("/api/auth/") ||
               path.startsWith("/swagger-ui/") ||
               path.startsWith("/v3/api-docs/") ||
               path.startsWith("/api-docs/") ||
               path.equals("/openapi.yaml");
    }

    private void handleUnauthenticated(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Authentication required\"}");
        response.setContentType("application/json");
    }
}
