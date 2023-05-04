package com.hotel.app.controller

import com.hotel.app.dto.BookingInfoDto
import com.hotel.app.kafka.service.KafkaProducerService
import com.hotel.app.models.Booking
import com.hotel.app.service.BookingService
import com.hotel.app.service.CustomerService
import com.hotel.app.service.RoomService
import com.hotel.app.validate.BookingValidate
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/booking")
@CrossOrigin("http://localhost:8081/")
class BookingController(val customerService: CustomerService, val roomService: RoomService, val bookingService: BookingService, val bookingValidate: BookingValidate,
    val kafkaProducerService: KafkaProducerService) {

    @RequestMapping(value = ["/"], method = [RequestMethod.GET, RequestMethod.POST])
    fun roomBooking(@Valid @RequestBody(required = false) bookingInfoDto: BookingInfoDto) : Mono<ResponseEntity<String>> {
        return Mono.zip(customerService.getByPhoneNumber(bookingInfoDto.phoneNumber),
            roomService.getByRoomTitle(bookingInfoDto.roomTitle))
            .flatMap { tuple ->
                val customer = tuple.t1
                val room = tuple.t2
                bookingValidate.validBooking(bookingInfoDto, room)
                    .flatMap { isValid ->
                        if (isValid) {
                            val booking = Booking(
                                null,
                                customer.id,
                                room.id,
                                bookingInfoDto.arrivalDate,
                                bookingInfoDto.departureDate,
                                bookingService.getCost(bookingInfoDto, room)
                            )
                            bookingService.save(booking).subscribe { println("Saved") }
                            kafkaProducerService.sendMessage(bookingInfoDto)
                            Mono.just(ResponseEntity.ok("Success"))
                        } else {
                            Mono.just(ResponseEntity.badRequest().build())
                        }
                    }
            }
            .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()))
    }
}