package com.hotel.app.controller

import com.hotel.app.dto.BookingInfoDto
import com.hotel.app.kafka.service.KafkaProducerService
import com.hotel.app.models.Booking
import com.hotel.app.models.Customer
import com.hotel.app.models.Room
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
        return customerService.getByPhoneNumber(bookingInfoDto.phoneNumber)
            .flatMap { customer: Customer ->
                roomService.getByRoomTitle(bookingInfoDto.roomTitle)
                    .flatMap { room: Room ->
                        bookingValidate.validBooking(bookingInfoDto, room)
                            .map { isValid: Boolean ->
                                if (isValid) {
                                    val booking = Booking(
                                        null,
                                        customer.id,
                                        room.id,
                                        bookingInfoDto.arrivalDate,
                                        bookingInfoDto.departureDate,
                                        bookingService.getCost(bookingInfoDto, room)
                                            .toFuture().get()
                                    )
                                    bookingService.save(booking).subscribe {
                                        println("Saved")
                                    }
                                    kafkaProducerService.sendMessage(bookingInfoDto)
                                    return@map ResponseEntity.ok("Success")
                                } else {
                                    return@map ResponseEntity.badRequest().build()
                                }
                            }
                    }
            }
            .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()))
    }
}