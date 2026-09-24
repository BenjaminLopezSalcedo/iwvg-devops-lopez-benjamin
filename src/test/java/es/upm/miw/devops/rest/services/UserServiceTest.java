package es.upm.miw.devops.rest.services;

import es.upm.miw.devops.rest.models.User;
import es.upm.miw.devops.rest.dtos.UserUpdateDto;
import es.upm.miw.devops.rest.repositories.UserRepository;
import es.upm.miw.devops.rest.models.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

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
                Role.CUSTOMER
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
                Role.CUSTOMER);

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
                Role.CUSTOMER);

        when(userRepository.findAll()).thenReturn(List.of(billableUser, nonBillableUser));

        List<User> result = userService.findByBillable(true);

        assertThat(result).containsExactly(billableUser);
    }

    @Test
    void testFindNonBillable() {
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
                Role.CUSTOMER);

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
                Role.CUSTOMER);

        when(userRepository.findAll()).thenReturn(List.of(billableUser, nonBillableUser));

        List<User> result = userService.findByBillable(false);

        assertThat(result).containsExactly(nonBillableUser);
    }

    @Test
    void testDeleteById() {
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
                Role.CUSTOMER
        );

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.deleteById("1");

        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteById("999"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id 999 not found");

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testSetActiveTrue() {
        User user = new User(
                "1",
                "John",
                "Smith",
                "john.smith@example.com",
                "IDENTITY1",
                "Main Street 1",
                "Madrid",
                "Madrid",
                "28001",
                false,
                Role.CUSTOMER
        );

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.setActive("1", true);

        assertThat(user.isActive()).isTrue();
        verify(userRepository).save(user);
    }

    @Test
    void testSetActiveFalse() {
        User user = new User(
                "1",
                "John",
                "Smith",
                "john.smith@example.com",
                "IDENTITY1",
                "Main Street 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                Role.CUSTOMER
        );

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.setActive("1", false);

        assertThat(user.isActive()).isFalse();
        verify(userRepository).save(user);
    }

    @Test
    void testSetActiveNotFound() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.setActive("999", true))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id 999 not found");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdate() {
        User user = new User(
                "1",
                "John",
                "Smith",
                "john.smith@example.com",
                "IDENTITY1",
                "Main Street 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                Role.CUSTOMER
        );

        UserUpdateDto userUpdateDto = new UserUpdateDto(
                "Benjamin",
                "Lopez",
                "benjamin@example.com",
                "IDENTITY2",
                "New Street 2",
                "Barcelona",
                "Barcelona",
                "08001",
                false,
                Role.ADMIN
        );

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.update("1", userUpdateDto);

        assertThat(user.getFirstName()).isEqualTo("Benjamin");
        assertThat(user.getFamilyName()).isEqualTo("Lopez");
        assertThat(user.getEmail()).isEqualTo("benjamin@example.com");
        assertThat(user.getIdentity()).isEqualTo("IDENTITY2");
        assertThat(user.getAddress()).isEqualTo("New Street 2");
        assertThat(user.getCity()).isEqualTo("Barcelona");
        assertThat(user.getProvince()).isEqualTo("Barcelona");
        assertThat(user.getPostalCode()).isEqualTo("08001");
        assertThat(user.isActive()).isFalse();
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);

        verify(userRepository).save(user);
    }

    @Test
    void testUpdateNotFound() {
        UserUpdateDto userUpdateDto = new UserUpdateDto(
                "Benjamin",
                "Lopez",
                "benjamin@example.com",
                "IDENTITY2",
                "New Street 2",
                "Barcelona",
                "Barcelona",
                "08001",
                false,
                Role.ADMIN
        );

        when(userRepository.findById("999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update("999", userUpdateDto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id 999 not found");

        verify(userRepository, never()).save(any(User.class));
    }
}
