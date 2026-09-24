package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testFindById() {
        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.firstName").isNotEmpty()
                .jsonPath("$.familyName").isNotEmpty()
                .jsonPath("$.email").value(email ->
                        assertThat(email.toString()).endsWith("@example.com"));
    }

    @Test
    void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/user/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindBillable() {
        webTestClient.get()
                .uri("/user?billable=true")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].firstName").isNotEmpty()
                .jsonPath("$[0].familyName").isNotEmpty()
                .jsonPath("$[0].email").isNotEmpty()
                .jsonPath("$[0].identity").isNotEmpty()
                .jsonPath("$[0].address").isNotEmpty()
                .jsonPath("$[0].city").isNotEmpty()
                .jsonPath("$[0].province").isNotEmpty()
                .jsonPath("$[0].postalCode").isNotEmpty();
    }

    @Test
    void testFindNonBillable() {
        webTestClient.get()
                .uri("/user?billable=false")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[?(@.id == '11')].email").doesNotExist()
                .jsonPath("$[?(@.id == '12')].address").isEqualTo("   ")
                .jsonPath("$[?(@.id == '13')].email").doesNotExist()
                .jsonPath("$[?(@.id == '13')].address").isEqualTo("   ");
    }

    @Test
    void testDeleteById() {
        webTestClient.delete()
                .uri("/user/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteByIdNotFound() {
        webTestClient.delete()
                .uri("/user/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSetActiveTrue() {
        webTestClient.put()
                .uri("/user/1/active")
                .bodyValue(true)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    void testSetActiveFalse() {
        webTestClient.put()
                .uri("/user/1/active")
                .bodyValue(false)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(false);
    }

    @Test
    void testSetActiveNotFound() {
        webTestClient.put()
                .uri("/user/999/active")
                .bodyValue(true)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdate() {
        webTestClient.put()
                .uri("/user/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "firstName": "Benjamin",
                        "familyName": "Lopez",
                        "email": "benjamin.updated@example.com",
                        "identity": "UPDATED123",
                        "address": "Updated Street 10",
                        "city": "Barcelona",
                        "province": "Barcelona",
                        "postalCode": "08010",
                        "active": false,
                        "role": "ADMIN"
                    }
                    """)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/user/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("Benjamin")
                .jsonPath("$.familyName").isEqualTo("Lopez")
                .jsonPath("$.email").isEqualTo("benjamin.updated@example.com")
                .jsonPath("$.identity").isEqualTo("UPDATED123")
                .jsonPath("$.address").isEqualTo("Updated Street 10")
                .jsonPath("$.city").isEqualTo("Barcelona")
                .jsonPath("$.province").isEqualTo("Barcelona")
                .jsonPath("$.postalCode").isEqualTo("08010")
                .jsonPath("$.active").isEqualTo(false)
                .jsonPath("$.role").isEqualTo("ADMIN");
    }

    @Test
    void testUpdateNotFound() {
        webTestClient.put()
                .uri("/user/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "firstName": "Benjamin",
                        "familyName": "Lopez",
                        "email": "benjamin@example.com",
                        "identity": "12345678A",
                        "address": "Main Street 1",
                        "city": "Madrid",
                        "province": "Madrid",
                        "postalCode": "28001",
                        "active": true,
                        "role": "CUSTOMER"
                    }
                    """)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateActive() {
        webTestClient.patch()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    [
                        {
                            "id": "2",
                            "active": false
                        },
                        {
                            "id": "3",
                            "active": true
                        }
                    ]
                    """)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/user/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(false);

        webTestClient.get()
                .uri("/user/3")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    void testUpdateActiveNotFound() {
        webTestClient.patch()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    [
                        {
                            "id": "999",
                            "active": false
                        }
                    ]
                    """)
                .exchange()
                .expectStatus().isNotFound();
    }
}