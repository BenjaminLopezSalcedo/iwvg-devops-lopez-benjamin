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

        when(userRepository.existsById(anyString())).thenReturn(false);

        UserSeeder userSeeder = new UserSeeder();
        CommandLineRunner runner = userSeeder.seedUsers(userRepository);

        runner.run();

        verify(userRepository, times(13)).save(any(User.class));
    }

    @Test
    void testSeedUsersWhenDatabaseIsNotEmpty() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.existsById(anyString())).thenReturn(true);

        UserSeeder userSeeder = new UserSeeder();
        CommandLineRunner runner = userSeeder.seedUsers(userRepository);

        runner.run();

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testSeedUsersWhenSomeUsersAreMissing() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.existsById(anyString())).thenReturn(true);
        when(userRepository.existsById("1")).thenReturn(false);
        when(userRepository.existsById("2")).thenReturn(false);
        when(userRepository.existsById("12")).thenReturn(false);

        UserSeeder userSeeder = new UserSeeder();
        CommandLineRunner runner = userSeeder.seedUsers(userRepository);

        runner.run();

        verify(userRepository, times(3)).save(any(User.class));
    }
}