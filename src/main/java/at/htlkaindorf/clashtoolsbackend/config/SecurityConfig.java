package at.htlkaindorf.clashtoolsbackend.config;

import at.htlkaindorf.clashtoolsbackend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration class for Spring Security settings.
 * This class configures security aspects of the application including:
 * - Security filter chain
 * - CORS configuration
 * - Password encoding
 * - Authentication manager
 * - JWT authentication filter
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Service for JWT operations.
     */
    private final JwtService jwtService;

    /**
     * Service for loading user details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Flag indicating whether the application is running in development mode.
     * In development mode, security restrictions are relaxed.
     */
    @Value("${security.development-mode:false}")
    private boolean developmentMode;

    /**
     * Configures the security filter chain for the application.
     * This method sets up:
     * - CORS configuration
     * - CSRF protection (disabled)
     * - Request authorization rules
     * - JWT authentication filter
     *
     * In development mode, all security restrictions are disabled.
     *
     * @param http The HttpSecurity to configure
     * @return The configured SecurityFilterChain
     * @throws Exception If an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable);

        if (developmentMode) {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            System.out.println("Running in DEVELOPMENT MODE - All endpoints are accessible without authentication");
        } else {
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**",
                                     "/webjars/**", "/openapi.yaml", "/api-docs/swagger-config").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
            );

            http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        return http.build();
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     * This configuration allows requests from specified origins and with specific methods and headers.
     *
     * @return A configured CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:5173", "http://localhost:8080"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Provides a password encoder for the application.
     * Uses BCrypt hashing algorithm for password encoding.
     *
     * @return A BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an authentication manager for the application.
     *
     * @param configuration The authentication configuration
     * @return The configured AuthenticationManager
     * @throws Exception If an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Creates a JWT authentication filter for the application.
     * This filter intercepts requests and authenticates users based on JWT tokens.
     *
     * @return A configured JwtAuthenticationFilter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }
}
