package es.upm.miw.devops.rest.configuration;

import es.upm.miw.devops.rest.models.User;
import es.upm.miw.devops.rest.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class UserSeeder {

    private static final int NUMBER_OF_USERS = 10;
    private static final Random RANDOM = new Random();

    private static final List<String> FIRST_NAMES = List.of(
            "James",
            "Emma",
            "Daniel",
            "Olivia",
            "Michael",
            "Sophia",
            "William",
            "Emily",
            "Alexander",
            "Charlotte"
    );

    private static final List<String> FAMILY_NAMES = List.of(
            "Smith",
            "Johnson",
            "Williams",
            "Brown",
            "Jones",
            "Garcia",
            "Miller",
            "Davis",
            "Wilson",
            "Taylor"
    );

    private static final List<String> CITIES = List.of(
            "Madrid",
            "Barcelona",
            "Valencia",
            "Seville",
            "Bilbao"
    );

    private static final List<String> PROVINCES = List.of(
            "Madrid",
            "Barcelona",
            "Valencia",
            "Seville",
            "Bizkaia"
    );

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {

                for (int i = 1; i <= NUMBER_OF_USERS; i++) {
                    String firstName = randomElement(FIRST_NAMES, RANDOM);
                    String familyName = randomElement(FAMILY_NAMES, RANDOM);
                    String city = randomElement(CITIES, RANDOM);
                    String province = randomElement(PROVINCES, RANDOM);

                    User user = new User(
                            String.valueOf(i),
                            firstName,
                            familyName,
                            firstName.toLowerCase() + "." + familyName.toLowerCase() + i + "@example.com",
                            "IDENTITY" + i,
                            "Main Street " + i,
                            city,
                            province,
                            String.valueOf(28000+i),
                            RANDOM.nextBoolean(),
                            i % 2 == 0 ? "USER" : "ADMIN"
                    );

                    userRepository.save(user);
                }
            }
        };
    }

    private String randomElement(List<String> values, Random random) {
        return values.get(random.nextInt(values.size()));
    }

    /*@Bean
    CommandLineRunner seedUsers(UserRepository userRepository) {
        return args -> {
            System.out.println(">>> USER SEEDER STARTED");

            if (userRepository.count() == 0) {
                Random random = new Random();

                for (int i = 1; i <= 10; i++) {
                    User user = new User(
                            String.valueOf(i),
                            "FirstName" + i,
                            "FamilyName" + i,
                            "user" + i + "@example.com",
                            "IDENTITY" + i,
                            "Address " + i,
                            "City" + i,
                            "Province" + i,
                            "2800" + i,
                            random.nextBoolean(),
                            random.nextInt() % 2 == 0 ? "USER" : "ADMIN"
                    );

                    userRepository.save(user);
                }

                System.out.println(">>> USERS SEEDED");
            }
        };
    }*/
}
