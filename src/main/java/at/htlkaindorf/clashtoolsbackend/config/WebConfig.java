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
}
