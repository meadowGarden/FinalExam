package lt.techin.exam.config;

import lt.techin.exam.entity.Advertisement;
import lt.techin.exam.entity.AdvertisementCategory;
import lt.techin.exam.entity.User;
import lt.techin.exam.repository.AdvertisementCategoryRepository;
import lt.techin.exam.repository.AdvertisementRepository;
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
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementCategoryRepository advertisementCategoryRepository;

    public InitialDataConfig(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AdvertisementRepository advertisementRepository,
            AdvertisementCategoryRepository advertisementCategoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.advertisementRepository = advertisementRepository;
        this.advertisementCategoryRepository = advertisementCategoryRepository;
    }

    @Bean
    public CommandLineRunner initialDataRunner() {
        return runner -> {
            addUsers();
            addAds();
            addAdCategory();
        };
    }


    public void addAdCategory() {
        AdvertisementCategory adCat01 = AdvertisementCategory.builder()
                .categoryName("gyvūnai")
                .build();
        advertisementCategoryRepository.save(adCat01);










    }

    public void addAds() {
        final Advertisement ad01 = Advertisement.builder()
                .adName("parduodu drambliuką")
                .adDescription("beveik naujas, pilkos spalvos")
                .price(15267)
                .city("Kaunas")
                .build();
        advertisementRepository.save(ad01);

        final Advertisement ad02 = Advertisement.builder()
                .adName("plaunu langus")
                .adDescription("atsivežu savo priemones, taikau nuolaidas senjorams")
                .price(20)
                .city("Klaipėda")
                .build();
        advertisementRepository.save(ad02);

        final Advertisement ad03 = Advertisement.builder()
                .adName("persikrautimo paslaugos visoje Lietuvoje")
                .adDescription("40, 65 ir 90 kubų talpos automobiliai, galutinė kaina priklauso nuo kilometrų")
                .price(150)
                .build();
        advertisementRepository.save(ad03);
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
