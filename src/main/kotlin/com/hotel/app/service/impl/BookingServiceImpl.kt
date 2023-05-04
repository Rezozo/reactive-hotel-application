package com.hotel.app.service.impl

import com.hotel.app.dto.BookingInfoDto
import com.hotel.app.enums.Direction
import com.hotel.app.models.Booking
import com.hotel.app.models.Room
import com.hotel.app.repository.BookingRepository
import com.hotel.app.service.BookingService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class BookingServiceImpl : BookingService {

    private var bookingRepository : BookingRepository

    constructor(bookingRepository: BookingRepository) {
        this.bookingRepository = bookingRepository
    }

    override fun getByPhoneNumber(phoneNumber: String): Flux<BookingInfoDto> {
        return bookingRepository.findBookingInfoOne(phoneNumber)
    }

    override fun getAll(cost: Direction?, arrivalDate: Direction?): Flux<BookingInfoDto> {
        if (cost == null && arrivalDate != null) {
            return bookingRepository.findBookingInfoAllOrderByArrival(arrivalDate)
        } else if(cost != null && arrivalDate == null) {
            return bookingRepository.findBookingInfoAllOrderByPrice(cost);
        }
        return bookingRepository.findBookingInfoAll();
    }

    override fun getStats(thisMonth: Boolean?): Flux<Map<String, Int>> {
        if (thisMonth != null) {
            return bookingRepository.findStatsBookingThisMonth()
        }
        return bookingRepository.findStatsBooking()
    }

    override fun getArrivalDates(id: Int): Flux<LocalDate> {
        return bookingRepository.findBookingArrivalByRoomId(id)
    }

    override fun getDepartureDates(id: Int): Flux<LocalDate> {
        return bookingRepository.findBookingDepartureByRoomId(id)
    }

    override fun save(booking: Booking): Mono<Booking> {
        return bookingRepository.save(booking)
    }

    override fun deleteById(id: Int): Mono<Void> {
        return bookingRepository.deleteById(id)
    }

    override fun canBook(bookingInfoDto: BookingInfoDto, arrivalDates: Flux<LocalDate>,
                         departureDates: Flux<LocalDate>): Mono<Boolean> {
        return Flux.zip(arrivalDates, departureDates)
            .filter { dates ->
                (!bookingInfoDto.departureDate.isBefore(dates.t1) && !bookingInfoDto.arrivalDate.isAfter(dates.t2))
            }
            .hasElements()
    }

    override fun canBookInThisSegment(arrivalDate: LocalDate, departureDate: LocalDate,
                                      arrivalDates: Flux<LocalDate>, departureDates: Flux<LocalDate>): Mono<Boolean> {
        return Flux.zip(arrivalDates, departureDates)
            .filter { dates ->
                !departureDate.isBefore(dates.t1) && !arrivalDate.isAfter(dates.t2)
            }
            .hasElements()
    }

    override fun getCost(bookingInfoDto: BookingInfoDto, room: Room): Int {
        val costPerDay : Int = room.price
        val arrivalDate : LocalDate = bookingInfoDto.arrivalDate
        val departureDate : LocalDate = bookingInfoDto.departureDate

        var days : Long = ChronoUnit.DAYS.between(arrivalDate, departureDate)
        if (days <= 0) days = 1;
        return (days * costPerDay).toInt()
    }
}