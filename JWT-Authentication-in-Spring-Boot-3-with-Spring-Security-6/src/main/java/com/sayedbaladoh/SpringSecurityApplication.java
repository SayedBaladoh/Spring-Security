package com.sayedbaladoh;

import com.sayedbaladoh.enums.RoleName;
import com.sayedbaladoh.model.Role;
import com.sayedbaladoh.model.User;
import com.sayedbaladoh.repository.RoleRepository;
import com.sayedbaladoh.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDb(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {

            Role superAdminRole = new Role();
            superAdminRole.setName(RoleName.ROLE_SUPER_ADMIN);
            superAdminRole.setDescription("Super Administrator role");
            roleRepository.save(superAdminRole);

            Role adminRole = new Role();
            adminRole.setName(RoleName.ROLE_ADMIN);
            adminRole.setDescription("Administrator role");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName(RoleName.ROLE_USER);
            userRole.setDescription("Default user role");
            roleRepository.save(userRole);

            User superAdminUser = new User();
            superAdminUser.setUsername("super_admin1");
            superAdminUser.setFirstName("Ahmed");
            superAdminUser.setLastName("Mohamed");
            superAdminUser.setRoles(Set.of(superAdminRole));
            superAdminUser.setPassword(passwordEncoder.encode("12345"));
            userRepository.save(superAdminUser);

            User adminUser = new User();
            adminUser.setUsername("admin1");
            adminUser.setFirstName("Mohamed");
            adminUser.setLastName("Mohamed");
            adminUser.setRoles(Set.of(adminRole));
            adminUser.setPassword(passwordEncoder.encode("12345"));
            userRepository.save(adminUser);

            User user = new User();
            user.setUsername("user1");
            user.setFirstName("Mahmoud");
            user.setLastName("Ahmed");
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("12345"));
            userRepository.save(user);
        };
    }
}
