package at.htlkaindorf.clashtoolsbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Configuration
 * Configures web-related settings including static resource handling and view controllers.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures resource handlers for serving static resources.
     * This method sets up handlers for Swagger UI resources, including:
     * - Swagger UI web interface
     * - Swagger UI HTML page
     * - Web JAR resources
     * - OpenAPI specification file
     *
     * @param registry The ResourceHandlerRegistry to configure
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                .resourceChain(false);

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui/index.html");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/openapi.yaml")
                .addResourceLocations("classpath:/openapi.yaml");
    }

    /**
     * Configures view controllers for the application.
     * This method sets up a redirect from /swagger-ui to the Swagger UI HTML page.
     *
     * @param registry The ViewControllerRegistry to configure
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui.html");
    }
}
