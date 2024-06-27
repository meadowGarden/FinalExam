package lt.techin.exam.config;

import lt.techin.exam.entity.User;
import lt.techin.exam.repository.UserRepository;
import lt.techin.exam.utilities.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialDataConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialDataConfig(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initialDataRunner() {
        return runner -> {
            addUsers();
        };
    }



    public void addUsers() {
        final User user00 = User.builder()
                .firstName("King")
                .lastName("Bradley")
                .email("king@mail.com")
                .userRole(UserRole.ADMINISTRATOR)
                .password(passwordEncoder.encode("passw"))
                .build();
        userRepository.save(user00);

        final User user01 = User.builder()
                .firstName("Roy")
                .lastName("Mustang")
                .email("rmust@mail.com")
                .userRole(UserRole.USER)
                .password(passwordEncoder.encode("passw"))
                .build();
        userRepository.save(user01);

        final User user02 = User.builder()
                .firstName("Riza")
                .lastName("Hawkeye")
                .email("riza@mail.com")
                .userRole(UserRole.USER)
                .password(passwordEncoder.encode("passw"))
                .build();
        userRepository.save(user02);

        final User user03 = User.builder()
                .firstName("Jean")
                .lastName("Havoc")
                .email("jean@mail.com")
                .userRole(UserRole.USER)
                .password(passwordEncoder.encode("passw"))
                .build();
        userRepository.save(user03);
    }

}
