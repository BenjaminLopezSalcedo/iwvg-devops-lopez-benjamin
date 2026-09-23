package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

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
}