package com.hotel.app.reactivehotelapplication.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.dto.ReviewInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ReviewControllerTests {
    @Autowired
    private WebTestClient webClient;

    private String token;

    @BeforeEach
    public void auth() {
        AuthenticationRequest requestEntity = new AuthenticationRequest("oksana96@yandex.ru", "1234");
        webClient.post().uri("/auth/authenticate")
                .body(BodyInserters.fromValue(requestEntity))
                .exchange()
                .expectBody(AuthenticationResponse.class)
                .consumeWith(response -> token = response.getResponseBody().getToken());
    }

    @Test
    void getAllReview_success() {
        webClient.get().uri("/reviews/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReviewInfoDto.class);
    }

    @Test
    void addReview_success() {
        ReviewInfoDto review = new ReviewInfoDto(null, "Flerova Oksana Antonovna", "oksana96@yandex.ru", (byte) 4, "Good hotel");

        webClient.post().uri("/reviews/add")
                .bodyValue(review)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void addReview_failForbidden() {
        ReviewInfoDto review = new ReviewInfoDto(null, "Flerova Oksana Antonovna", "oksana96@yandex.ru", (byte) 4, "Good hotel");

        webClient.post().uri("/reviews/add")
                .bodyValue(review)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void updateReview_success() {
        ReviewInfoDto review = new ReviewInfoDto(40, "Flerova Oksana Antonovna", "oksana96@yandex.ru", (byte) 2, "Bad hotel");

        webClient.put().uri("/reviews/update")
                .bodyValue(review)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }
}
