package com.hotel.app.service

import com.hotel.app.models.Customer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CustomerService {
    fun getById(id: Int) : Mono<Customer>
    fun getByEmail(email: String) : Mono<Customer>
    fun getByPhoneNumber(phoneNumber: String) : Mono<Customer>
    fun getAll() : Flux<Customer>
    fun save(customer: Customer) : Mono<Customer>
    fun deleteById(id: Int) : Mono<Void>
    fun updateCustomer(customer: Customer) : Mono<Customer>
    fun existPhoneNumber(phoneNumber: String) : Mono<Boolean>
    fun existEmail(email: String) : Mono<Boolean>
}