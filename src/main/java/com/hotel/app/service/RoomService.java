package com.hotel.app.service;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.enums.Direction;
import com.hotel.app.models.Room;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface RoomService {
    Mono<Room> getByRoomTitle(String title);
    Mono<RoomInfoDto> getByTitleAndType(String typeTitle, String roomTitle);
    Flux<RoomInfoDto> getAllByType(String type_title, Boolean status, Direction direction, LocalDate arrivalDate, LocalDate departureDate);
    Flux<RoomInfoDto> getAll(Boolean status, Direction direction, LocalDate arrivalDate, LocalDate departureDate);
    Mono<Room> save(Room room);
    Mono<Room> updateStatusById(Integer id);
    Mono<Void> deleteById(Integer id);
}
