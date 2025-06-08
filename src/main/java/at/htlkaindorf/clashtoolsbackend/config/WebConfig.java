package at.htlkaindorf.clashtoolsbackend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web MVC Configuration
 * Configures web-related settings including CORS, authentication, and static resource handling.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Add resource handlers for Swagger UI
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                .resourceChain(false);

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui/index.html");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // Add handler for OpenAPI definition file
        registry.addResourceHandler("/openapi.yaml")
                .addResourceLocations("classpath:/openapi.yaml");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect from /swagger-ui to /swagger-ui.html
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui.html");
    }

    /**
     * Configure CORS for the application.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5173", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * Register the custom authentication filter.
     */
    @Bean
    public FilterRegistrationBean<CustomAuthFilter> customAuthFilterRegistration(CustomAuthFilter filter) {
        FilterRegistrationBean<CustomAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1); // High priority but after CORS filter
        return registration;
    }
}
