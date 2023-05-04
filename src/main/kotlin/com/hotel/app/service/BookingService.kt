package com.hotel.app.service

import com.hotel.app.dto.BookingInfoDto
import com.hotel.app.enums.Direction
import com.hotel.app.models.Booking
import com.hotel.app.models.Room
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

interface BookingService {
    fun getByPhoneNumber(phoneNumber: String) : Flux<BookingInfoDto>
    fun getAll(cost: Direction?, arrivalDate: Direction?): Flux<BookingInfoDto>
    fun getStats(thisMonth: Boolean?): Flux<Map<String, Int>>
    fun getArrivalDates(id: Int): Flux<LocalDate>
    fun getDepartureDates(id: Int): Flux<LocalDate>
    fun save(booking: Booking): Mono<Booking>
    fun deleteById(id: Int): Mono<Void>
    fun canBook(bookingInfoDto: BookingInfoDto, arrivalDates: Flux<LocalDate>, departureDates: Flux<LocalDate>): Mono<Boolean>
    fun canBookInThisSegment(arrivalDate: LocalDate, departureDate: LocalDate, arrivalDates: Flux<LocalDate>, departureDates: Flux<LocalDate>): Mono<Boolean>
    fun getCost(bookingInfoDto: BookingInfoDto, room: Room): Int
}