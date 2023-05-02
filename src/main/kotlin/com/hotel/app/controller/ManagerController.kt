package com.hotel.app.controller

import com.hotel.app.dto.BookingInfoDto
import com.hotel.app.dto.ReviewInfoDto
import com.hotel.app.models.Customer
import com.hotel.app.service.BookingService
import com.hotel.app.service.CustomerService
import com.hotel.app.service.ReviewService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/manager")
@CrossOrigin("http://localhost:8081/")
class ManagerController(val customerService: CustomerService, val bookingService: BookingService, val reviewService: ReviewService) {

    @RequestMapping(value = ["/customers"], method = [RequestMethod.GET])
    fun allCustomers() : Flux<Customer> {
        return customerService.getAll()
    }

    @RequestMapping(value = ["/bookings"], method = [RequestMethod.GET])
    fun allBookings(@RequestParam(required = false) directionPrice : String?, @RequestParam(required = false) directionDate: String?) : Flux<BookingInfoDto>  {
        return bookingService.getAll(directionPrice, directionDate)
    }

    @RequestMapping(value = ["/customer/book"], method = [RequestMethod.GET])
    fun customersBookings(@RequestParam phoneNumber: String) : Flux<BookingInfoDto> {
        return bookingService.getByPhoneNumber(phoneNumber)
    }

    @RequestMapping(value = ["/customer/book/del"], method = [RequestMethod.DELETE])
    fun customersBookingsDelete(@RequestParam id : Int) : Mono<Void> {
        return bookingService.deleteById(id)
    }

    @RequestMapping(value = ["/customer/review"], method = [RequestMethod.GET])
    fun customersReview(@RequestParam id: Int) : Mono<ReviewInfoDto> {
        return reviewService.getByIdInfo(id)
    }

    @RequestMapping(value = ["/customer/review/del"], method = [RequestMethod.DELETE])
    fun customersReviewDelete(@RequestParam id: Int) : Mono<Void> {
        return reviewService.deleteById(id)
    }
}