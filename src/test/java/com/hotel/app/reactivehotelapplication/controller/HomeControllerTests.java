package com.hotel.app.reactivehotelapplication.controller;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.models.RoomType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class HomeControllerTests {
    @Autowired
    private WebTestClient webClient;

    @Test
    void homeTypes_success() {
        webClient.get().uri("/myhotel/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RoomType.class);
    }

    @Test
    void allrooms_success() {
        webClient.get().uri(uriBuilder ->
                        uriBuilder.path("/myhotel/allrooms")
                                .queryParam("status", "true")
                                .queryParam("direction", "asc")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RoomInfoDto.class);
    }

    @Test
    void oneTypeRooms_success() {
        webClient.get().uri(uriBuilder ->
                uriBuilder.path("/myhotel/Single/rooms")
                        .queryParam("status","true")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RoomInfoDto.class);
    }

    @Test
    void oneRoom_success() {
        webClient.get().uri("/myhotel/Single/rooms/Luxury one")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RoomInfoDto.class);
    }
}
