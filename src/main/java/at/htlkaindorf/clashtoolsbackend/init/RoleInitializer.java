package at.htlkaindorf.clashtoolsbackend.init;

import at.htlkaindorf.clashtoolsbackend.constants.RoleConstants;
import at.htlkaindorf.clashtoolsbackend.pojos.Role;
import at.htlkaindorf.clashtoolsbackend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Initializer for application roles.
 * This configuration class ensures that all required roles defined in RoleConstants
 * are created in the database when the application starts.
 */
@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

    /**
     * Repository for accessing and manipulating Role entities.
     */
    private final RoleRepository roleRepository;

    /**
     * Initializes the application roles.
     * This bean creates an ApplicationRunner that ensures all roles defined in RoleConstants
     * exist in the database when the application starts. If a role doesn't exist, it will be created.
     *
     * @return An ApplicationRunner that initializes the roles
     */
    @Bean
    public ApplicationRunner initRoles() {
        return args -> {
            List<String> roles = Arrays.stream(RoleConstants.values())
                    .map(RoleConstants::getRoleName)
                    .toList();

            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    roleRepository.save(Role.builder().name(roleName).build());
                }
            }
        };
    }
}
