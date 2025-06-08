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

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Wird automatisch von Spring verwendet, wenn User laden

    @Value("${security.development-mode:false}")
    private boolean developmentMode;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable);

        if (developmentMode) {
            // In development mode, permit all requests without authentication
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            System.out.println("Running in DEVELOPMENT MODE - All endpoints are accessible without authentication");
        } else {
            // In production mode, require authentication for all endpoints except those explicitly permitted
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**",
                                     "/webjars/**", "/openapi.yaml", "/api-docs/swagger-config").permitAll() // Für Dev-Zwecke offen später has role
                    .requestMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
            );

            // Add JWT filter only in production mode
            http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:5173", "http://localhost:8080")); // Specific origins
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type")); // Expose headers to the frontend
        config.setAllowCredentials(true); // Enable credentials for authenticated requests
        config.setMaxAge(3600L); // Cache preflight requests for 1 hour to reduce OPTIONS requests

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }
}
