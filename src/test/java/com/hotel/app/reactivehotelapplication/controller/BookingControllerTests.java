package com.hotel.app.reactivehotelapplication.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.exceptions.RoomOccupiedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class BookingControllerTests {

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
    void booking_success() {
        LocalDate arrival = LocalDate.parse("2023-05-20");
        LocalDate departure = LocalDate.parse("2023-05-25");

        BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                "79239519735", "Luxury one", arrival, departure, null);

        webClient.post().uri("/booking/")
                .bodyValue(bookingInfoDto)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void booking_failOccupied() {
        assertThrows(RoomOccupiedException.class, () -> {
            LocalDate arrival = LocalDate.parse("2023-04-29");
            LocalDate departure = LocalDate.parse("2023-05-11");

            BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                    "79239519735", "Family luxury", arrival, departure, null);

            webClient.post().uri("/booking/")
                    .bodyValue(bookingInfoDto)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .exchange()
                    .expectStatus().is5xxServerError();
        });
    }
}
