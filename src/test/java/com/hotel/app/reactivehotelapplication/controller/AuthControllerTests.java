package com.hotel.app.reactivehotelapplication.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import org.apache.http.auth.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AuthControllerTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void register_Success() {
        RegisterRequest request = new RegisterRequest("Ivanov Ivan Ivanovich", "ivanov@gmail.com",
                "7967235321235", "1234");

        webClient.post().uri("/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void register_fail_IncorrectInfo() {
        RegisterRequest request = new RegisterRequest("Ivanov Ivan Ivanovich", "anastasiya.ketova@gmail.com",
                "7967235321235", "1234");

        webClient.post().uri("/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void authenticate_Success() {
        AuthenticationRequest request = new AuthenticationRequest("oksana96@yandex.ru", "1234");

        webClient.post().uri("/auth/authenticate")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void authenticate_IncorrectPassord() {
        AuthenticationRequest request = new AuthenticationRequest("oksana96@yandex.ru", "1234567547434");

        webClient.post().uri("/auth/authenticate")
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
