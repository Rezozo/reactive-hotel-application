package com.hotel.app.service.impl

import com.hotel.app.models.Customer
import com.hotel.app.repository.CustomerRepository
import com.hotel.app.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class CustomerServiceImpl : CustomerService{

    private var customerRepository : CustomerRepository

    @Autowired
    constructor(customerRepository: CustomerRepository) {
        this.customerRepository = customerRepository
    }

    override fun getById(id: Int): Mono<Customer> {
        return customerRepository.findById(id)
            .switchIfEmpty(Mono.empty())
    }

    override fun getByEmail(email: String): Mono<Customer> {
        return customerRepository.findByEmail(email)
            .switchIfEmpty(Mono.empty())
    }

    override fun getByPhoneNumber(phoneNumber: String): Mono<Customer> {
        return customerRepository.findByPhoneNumber(phoneNumber)
            .switchIfEmpty(Mono.empty())
    }

    override fun getAll(): Flux<Customer> {
        return customerRepository.findAll()
    }

    override fun save(customer: Customer): Mono<Customer> {
        return customerRepository.save(customer)
    }

    override fun deleteById(id: Int): Mono<Void> {
        return customerRepository.deleteById(id)
    }

    override fun updateCustomer(customer: Customer): Mono<Customer> {
        return customerRepository.findById(customer.id)
            .switchIfEmpty(Mono.empty())
            .flatMap { existCustomer ->
                existEmail(customer.email).flatMap { emailExists ->
                    existPhoneNumber(customer.phoneNumber).flatMap { phoneNumberExists ->
                        if ((existCustomer.email == customer.email || !emailExists) &&
                            (existCustomer.phoneNumber == customer.phoneNumber || !phoneNumberExists)) {
                            existCustomer.fullName = customer.fullName
                            existCustomer.email = customer.email
                            existCustomer.phoneNumber = customer.phoneNumber
                            customerRepository.save(existCustomer)
                        } else {
                            Mono.empty()
                        }
                    }
                }
            }
    }

    override fun existPhoneNumber(phoneNumber: String): Mono<Boolean> {
        return getByPhoneNumber(phoneNumber)
            .hasElement();
    }

    override fun existEmail(email: String): Mono<Boolean> {
        return getByEmail(email)
            .hasElement();
    }
}