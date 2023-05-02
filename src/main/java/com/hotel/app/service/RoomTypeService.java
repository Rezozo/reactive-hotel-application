package com.hotel.app.service;

import com.hotel.app.models.RoomType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoomTypeService {
    Mono<RoomType> getById(Integer id);
    Mono<RoomType> getByTitle(String title);
    Flux<RoomType> getAll();
    Mono<RoomType> save(RoomType roomType);
    Mono<Void> deleteById(Integer id);
}
