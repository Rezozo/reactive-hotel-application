package com.hotel.app.service.impl

import com.hotel.app.dto.ReviewInfoDto
import com.hotel.app.enums.Direction
import com.hotel.app.exceptions.ReviewExistException
import com.hotel.app.models.Customer
import com.hotel.app.models.Review
import com.hotel.app.repository.ReviewRepository
import com.hotel.app.service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ReviewServiceImpl : ReviewService {

    private var reviewrepository: ReviewRepository

    @Autowired
    constructor(reviewrepository: ReviewRepository) {
        this.reviewrepository = reviewrepository;
    }

    override fun getById(id: Int): Mono<Review> {
        return reviewrepository.findById(id)
            .switchIfEmpty(Mono.empty())
    }

    override fun getByIdInfo(id: Int): Mono<ReviewInfoDto> {
        return reviewrepository.findReviewInfoOneById(id)
    }

    override fun getByEmail(email: String): Mono<ReviewInfoDto> {
        return reviewrepository.findReviewInfoOneByEmail(email)
    }

    override fun getByPhoneNumber(phoneNumber: String): Mono<ReviewInfoDto> {
        return reviewrepository.findReviewInfoOneByPhone(phoneNumber)
    }

    override fun getAll(direction: Direction?): Flux<ReviewInfoDto> {
        if (direction != null) {
            return reviewrepository.findReviewInfoAllOrderByRateDesc(direction)
        } else {
            return reviewrepository.findReviewInfoAll()
        }
    }

    override fun save(review: Review): Mono<Review> {
        return reviewrepository.insert(review.id, review.customer, review.rate, review.feedback)
    }

    override fun updateRateAndFeedback(id: Int, rate: Byte, feedback: String): Mono<Review> {
        return reviewrepository.findById(id)
            .switchIfEmpty(Mono.empty())
            .flatMap { existReview ->
                existReview.rate = rate
                existReview.feedback = feedback
                reviewrepository.save(existReview)
            }
    }

    override fun deleteById(id: Int): Mono<Void> {
        return reviewrepository.deleteById(id)
    }

    override fun canReview(customer: Customer): Mono<Boolean> {
        return getById(customer.id)
            .flatMap<Boolean> {
                Mono.error(ReviewExistException())
            }.switchIfEmpty(Mono.just(true))
    }

    override fun canUpdate(customer: Customer): Mono<Boolean> {
        return getById(customer.id)
            .flatMap {
                Mono.just(true)
            }.switchIfEmpty(Mono.just(false))
    }
}