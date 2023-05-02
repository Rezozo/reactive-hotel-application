package com.hotel.app.service

import com.hotel.app.dto.ReviewInfoDto
import com.hotel.app.models.Customer
import com.hotel.app.models.Review
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReviewService {
    fun getById(id: Int) : Mono<Review>
    fun getByIdInfo(id: Int) : Mono<ReviewInfoDto>
    fun getByEmail(email: String) : Mono<ReviewInfoDto>
    fun getByPhoneNumber(phoneNumber: String) : Mono<ReviewInfoDto>
    fun getAll(direction: String?) : Flux<ReviewInfoDto>
    fun save(review: Review) : Mono<Review>
    fun updateRateAndFeetback(id: Int, rate: Byte, feedback: String) : Mono<Review>
    fun deleteById(id: Int) : Mono<Void>
    fun canReview(reviewDto : ReviewInfoDto, customer: Customer) : Mono<Boolean>
    fun canUpdate(reviewDto: ReviewInfoDto, customer: Customer) : Mono<Boolean>
}