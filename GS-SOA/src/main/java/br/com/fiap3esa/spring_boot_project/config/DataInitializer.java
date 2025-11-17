package br.com.fiap3esa.spring_boot_project.config;


import br.com.fiap3esa.spring_boot_project.entity.User;
import br.com.fiap3esa.spring_boot_project.enums.RoleName;
import br.com.fiap3esa.spring_boot_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:admin}")
    private String adminEmail;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = User.builder()
                        .email(adminEmail)
                        .fullName("Administrator")
                        .password(passwordEncoder.encode(adminPassword))
                        .roles(Set.of(RoleName.ROLE_ADMIN, RoleName.ROLE_USER))
                        .build();
                userRepository.save(admin);
                System.out.println("DataInitializer: admin criado -> " + adminEmail);
            } else {
                System.out.println("DataInitializer: admin já existe -> " + adminEmail);
            }
        } catch (Exception e) {
            System.err.println("DataInitializer: falha ao criar admin inicial - " + e.getMessage());
            e.printStackTrace();
        }
    }
}