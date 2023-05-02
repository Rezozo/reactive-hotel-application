package com.hotel.app.config

import com.hotel.app.repository.ReviewRepository
import com.hotel.app.service.impl.ReviewServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReviewConfig {
    @Bean
    fun reviewService(reviewRepository: ReviewRepository) : ReviewServiceImpl {
        return ReviewServiceImpl(reviewRepository)
    }
}