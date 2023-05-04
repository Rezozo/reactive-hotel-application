package com.hotel.app.service.impl;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.enums.Direction;
import com.hotel.app.models.Room;
import com.hotel.app.repository.RoomRepository;
import com.hotel.app.service.BookingService;
import com.hotel.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private BookingService bookingService;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
    }

    @Override
    public Mono<Room> getByRoomTitle(String title) {
        return roomRepository.findByTitle(title)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Room not found")));
    }

    @Override
    public Mono<RoomInfoDto> getByTitleAndType(String typeTitle, String roomTitle) {
        return roomRepository.findOneRoomByTypeAndTitle(typeTitle, roomTitle);
    }

    @Override
    public Flux<RoomInfoDto> getAllByType(String type_title, Boolean status, Direction direction, LocalDate arrivalDate, LocalDate departureDate) {
        if (arrivalDate == null || departureDate == null) {
            if (direction == null) {
                return roomRepository.findRoomByTypeAndStatus(type_title, status);
            } else {
                return roomRepository.findRoomByTypeAndStatusOrderByPrice(type_title, status, direction);
            }
        }

        if (direction == null) {
            return roomRepository.findRoomByTypeAndStatus(type_title, status)
                    .filterWhen(roomInfoDto -> bookingService.canBookInThisSegment(arrivalDate, departureDate,
                            bookingService.getArrivalDates(roomInfoDto.getId()), bookingService.getDepartureDates(roomInfoDto.getId())));
        } else {
            return roomRepository.findRoomByTypeAndStatusOrderByPrice(type_title, status, direction)
                    .filterWhen(roomInfoDto -> bookingService.canBookInThisSegment(arrivalDate, departureDate,
                            bookingService.getArrivalDates(roomInfoDto.getId()), bookingService.getDepartureDates(roomInfoDto.getId())));
        }
    }

    @Override
    public Flux<RoomInfoDto> getAll(Boolean status, Direction direction, LocalDate arrivalDate, LocalDate departureDate) {
        if (arrivalDate == null || departureDate == null) {
            if (direction == null) {
                return roomRepository.findRoomInfoAllByStatus(status);
            } else {
                return roomRepository.findRoomInfoAllByStatusOrderByPrice(status, direction);
            }
        }

        if (direction == null) {
            return roomRepository.findRoomInfoAllByStatus(status)
                    .filterWhen(roomInfoDto -> bookingService.canBookInThisSegment(arrivalDate, departureDate,
                            bookingService.getArrivalDates(roomInfoDto.getId()), bookingService.getDepartureDates(roomInfoDto.getId())));
        } else {
            return roomRepository.findRoomInfoAllByStatusOrderByPrice(status, direction)
                    .filterWhen(roomInfoDto -> bookingService.canBookInThisSegment(arrivalDate, departureDate,
                            bookingService.getArrivalDates(roomInfoDto.getId()), bookingService.getDepartureDates(roomInfoDto.getId())));
        }
    }

    @Override
    public Mono<Room> save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Mono<Room> updateStatusById(Integer id) {
        return roomRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(room -> {
                    if (room.getStatus()) {
                        room.setStatus(false);
                        roomRepository.save(room);
                    } else {
                        room.setStatus(true);
                        roomRepository.save(room);
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return roomRepository.deleteById(id);
    }
}
