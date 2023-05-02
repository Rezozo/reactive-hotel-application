package com.hotel.app.config

import com.hotel.app.repository.CustomerRepository
import com.hotel.app.service.impl.CustomerServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomerConfig {
    @Bean
    fun customerService(customerRepository: CustomerRepository) : CustomerServiceImpl {
        return CustomerServiceImpl(customerRepository)
    }
}