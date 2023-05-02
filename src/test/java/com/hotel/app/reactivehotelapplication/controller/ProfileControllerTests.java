package com.hotel.app.reactivehotelapplication.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.response.AuthenticationResponse;
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
public class ProfileControllerTests {

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
    void myProfile_success() {
        webClient.get().uri(uriBuilder ->
                uriBuilder.path("/profile/")
                        .queryParam("email", "oksana96@yandex.ru")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void booking_success() {
        webClient.get().uri(uriBuilder ->
                        uriBuilder.path("/profile/booking")
                                .queryParam("email", "oksana96@yandex.ru")
                                .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteBooking_success() {
        webClient.delete().uri(uriBuilder ->
                        uriBuilder.path("/profile/booking/delete")
                                .queryParam("id", "6")
                                .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }
}
