package es.upm.miw.devops.rest.models;

import es.upm.miw.devops.rest.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testConstructorAndAccessors() {
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

        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getFirstName()).isEqualTo("Benjamin");
        assertThat(user.getFamilyName()).isEqualTo("Lopez");
        assertThat(user.getEmail()).isEqualTo("benjamin@example.com");
        assertThat(user.getIdentity()).isEqualTo("12345678A");
        assertThat(user.getAddress()).isEqualTo("Main Street 1");
        assertThat(user.getCity()).isEqualTo("Madrid");
        assertThat(user.getProvince()).isEqualTo("Madrid");
        assertThat(user.getPostalCode()).isEqualTo("28001");
        assertThat(user.isActive()).isTrue();
        assertThat(user.getRole()).isEqualTo(Role.CUSTOMER);
    }

    @Test
    void testSetters() {
        User user = new User();

        user.setId("2");
        user.setFirstName("Emma");
        user.setFamilyName("Smith");
        user.setEmail("emma@example.com");
        user.setIdentity("12345678B");
        user.setAddress("Second Street 2");
        user.setCity("Barcelona");
        user.setProvince("Barcelona");
        user.setPostalCode("08002");
        user.setActive(false);
        user.setRole(Role.ADMIN);

        assertThat(user.getId()).isEqualTo("2");
        assertThat(user.getFirstName()).isEqualTo("Emma");
        assertThat(user.getFamilyName()).isEqualTo("Smith");
        assertThat(user.getEmail()).isEqualTo("emma@example.com");
        assertThat(user.getIdentity()).isEqualTo("12345678B");
        assertThat(user.getAddress()).isEqualTo("Second Street 2");
        assertThat(user.getCity()).isEqualTo("Barcelona");
        assertThat(user.getProvince()).isEqualTo("Barcelona");
        assertThat(user.getPostalCode()).isEqualTo("08002");
        assertThat(user.isActive()).isFalse();
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }
}