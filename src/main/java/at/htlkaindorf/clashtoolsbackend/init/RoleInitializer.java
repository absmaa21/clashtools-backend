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
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @Bean
    public ApplicationRunner initRoles() {
        return args -> {
            List<String> roles = Arrays.stream(RoleConstants.values())
                    .map(RoleConstants::getRoleName)
                    .collect(Collectors.toList());

            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    roleRepository.save(Role.builder().name(roleName).build());
                }
            }
        };
    }
}
