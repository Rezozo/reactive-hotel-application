package com.hotel.app.config

import com.hotel.app.repository.BookingRepository
import com.hotel.app.service.BookingService
import com.hotel.app.service.impl.BookingServiceImpl
import com.hotel.app.validate.BookingValidate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BookingConfig {
    @Bean
    fun bookingService(bookingRepository: BookingRepository) : BookingServiceImpl {
        return BookingServiceImpl(bookingRepository)
    }

    @Bean
    fun bookingValidate(bookingService: BookingService) : BookingValidate {
        return BookingValidate(bookingService)
    }
}