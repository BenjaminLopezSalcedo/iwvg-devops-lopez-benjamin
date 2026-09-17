package es.upm.miw.devops.rest.configuration;

import es.upm.miw.devops.rest.models.User;
import es.upm.miw.devops.rest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;

import static org.mockito.Mockito.*;

class UserSeederTest {

    @Test
    void testSeedUsersWhenDatabaseIsEmpty() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.count()).thenReturn(0L);

        UserSeeder userSeeder = new UserSeeder();
        CommandLineRunner runner = userSeeder.seedUsers(userRepository);

        runner.run();

        verify(userRepository, times(10)).save(any(User.class));
        verify(userRepository).count();
    }

    @Test
    void testSeedUsersWhenDatabaseIsNotEmpty() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.count()).thenReturn(1L);

        UserSeeder userSeeder = new UserSeeder();
        CommandLineRunner runner = userSeeder.seedUsers(userRepository);

        runner.run();

        verify(userRepository).count();
        verify(userRepository, never()).save(any(User.class));
    }
}