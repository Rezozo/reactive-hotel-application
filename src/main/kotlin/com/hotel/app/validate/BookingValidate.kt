package com.hotel.app.validate

import com.hotel.app.dto.BookingInfoDto
import com.hotel.app.exceptions.ArrivalException
import com.hotel.app.exceptions.DatePastException
import com.hotel.app.exceptions.MaxPeriodException
import com.hotel.app.exceptions.RoomOccupiedException
import com.hotel.app.models.Room
import com.hotel.app.service.BookingService
import org.springframework.beans.factory.annotation.Value
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

class BookingValidate {

    var bookingService: BookingService

    @Value("\${hotel.booking.maxdays}")
    private lateinit var maxDays: String

    fun getMaxDays(): Long {
        return maxDays.toLong()
    }

    constructor(bookingService: BookingService) {
        this.bookingService = bookingService
    }

    fun validBooking(bookingInfoDto: BookingInfoDto, room: Room) : Mono<Boolean> {
        val arrivalDates: Flux<LocalDate> = bookingService.getArrivalDates(room.id)
        val departureDates: Flux<LocalDate> = bookingService.getDepartureDates(room.id)

        val result: Mono<Boolean> = bookingService.canBook(bookingInfoDto, arrivalDates, departureDates)
        return result.flatMap { isValid ->
            if (!isValid) {
                return@flatMap Mono.error(RoomOccupiedException())
            }

            if (bookingInfoDto.arrivalDate.isAfter(bookingInfoDto.departureDate)) {
                return@flatMap Mono.error(ArrivalException())
            }

            val now: LocalDate = LocalDate.now()
            val arrivalInstant: LocalDate = bookingInfoDto.arrivalDate

            if (now.isAfter(arrivalInstant)) {
                return@flatMap Mono.error(DatePastException())
            }

            val maxDate: LocalDate = arrivalInstant.plusDays(getMaxDays())

            if (bookingInfoDto.departureDate.isAfter(maxDate)) {
                return@flatMap Mono.error(MaxPeriodException())
            }

            Mono.just(true)
        }
    }

}