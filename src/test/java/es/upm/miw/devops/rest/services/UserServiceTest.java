package es.upm.miw.devops.rest.services;

import es.upm.miw.devops.rest.models.User;
import es.upm.miw.devops.rest.repositories.UserRepository;
import es.upm.miw.devops.rest.services.UserNotFoundException;
import es.upm.miw.devops.rest.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindById() {
        User user = new User(
                "1",
                "Benjamin",
                "Lopez",
                "benjamin@example.com",
                "12345678A",
                "Main Street 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                "USER"
        );

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userService.findById("1");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById("1"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id 1 not found");
    }

    @Test
    void testFindBillable() {
        User billableUser = new User(
                "1",
                "Benjamin",
                "Lopez",
                "benjamin@example.com",
                "12345678A",
                "Street 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                "USER");

        User nonBillableUser = new User(
                "2",
                "Benjamin",
                "Lopez",
                null,
                "12345678B",
                "Street 2",
                "Madrid",
                "Madrid",
                "28002",
                true,
                "USER");

        when(userRepository.findAll()).thenReturn(List.of(billableUser, nonBillableUser));

        List<User> result = userService.findBillable();

        assertThat(result).containsExactly(billableUser);
    }
}
