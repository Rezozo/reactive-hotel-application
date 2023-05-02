package com.hotel.app.repository;

import com.hotel.app.models.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, Integer> {
    Mono<Customer> findByEmail(String email);
    Mono<Customer> findByPhoneNumber(String phoneNumber);
}
