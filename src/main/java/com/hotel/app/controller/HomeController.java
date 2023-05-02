package com.hotel.app.controller;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.models.RoomType;
import com.hotel.app.service.RoomService;
import com.hotel.app.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/myhotel")
@AllArgsConstructor
public class HomeController {
    private RoomService roomService;
    private RoomTypeService roomTypeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Flux<RoomType> homeTypes() {
        return roomTypeService.getAll();
    }

    @RequestMapping(value = "/allrooms", method = RequestMethod.GET)
    public Flux<RoomInfoDto> homeRooms(@RequestParam(required = false) Boolean status, @RequestParam(required = false) String direction,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate arrivalDate,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate departureDate) {
        return roomService.getAll(status, direction, arrivalDate, departureDate);
    }

    @RequestMapping(value = "/{typetitle}/rooms", method = RequestMethod.GET)
    public Flux<RoomInfoDto> homeOneType(@PathVariable String typetitle, @RequestParam(required = false) Boolean status,
                                         @RequestParam(required = false) String direction,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate arrivalDate,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate departureDate) {
        return roomService.getAllByType(typetitle, status, direction, arrivalDate, departureDate)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @RequestMapping(value = "/{typetitle}/rooms/{title}", method = RequestMethod.GET)
    public Mono<ResponseEntity<RoomInfoDto>> homeOneRoom(@PathVariable String typetitle, @PathVariable String title) {
        return roomService.getByTitleAndType(typetitle, title)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
