package com.hotel.app.controller

import com.hotel.app.dto.BookingInfoDto
import com.hotel.app.models.Customer
import com.hotel.app.service.BookingService
import com.hotel.app.service.CustomerService
import com.hotel.app.service.UsersService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/profile")
@CrossOrigin("http://localhost:8081/")
class ProfileController(val customerService: CustomerService, val usersService: UsersService, val bookingService: BookingService) {

    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun myProfile(@RequestParam email: String) : Mono<Customer> {
        return customerService.getByEmail(email)
    }

    @RequestMapping(value = ["/update"], method = [RequestMethod.PUT])
    fun updateCustomer(@Valid @RequestBody customer: Customer) : Mono<ResponseEntity<String>> {
        customerService.updateCustomer(customer)
        usersService.updateById(customer.id, customer.email, customer.fullName)
        return Mono.just(ResponseEntity.ok("Success"))
    }

    @RequestMapping(value = ["/booking"], method = [RequestMethod.GET])
    fun customersBooking(@RequestParam email: String) : Flux<BookingInfoDto> {
        val customer : Mono<Customer> = customerService.getByEmail(email)
        return customer.flatMapMany { customer ->
            bookingService.getByPhoneNumber(customer.phoneNumber)
        }
    }

    @RequestMapping(value = ["/booking/delete"], method = [RequestMethod.DELETE])
    fun customersBookingsDelete(@RequestParam id: Int) : Mono<Void> {
        return bookingService.deleteById(id)
    }
}