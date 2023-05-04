package com.hotel.app.controller

import com.hotel.app.dto.ReviewInfoDto
import com.hotel.app.enums.Direction
import com.hotel.app.exceptions.ReviewExistException
import com.hotel.app.models.Review
import com.hotel.app.service.CustomerService
import com.hotel.app.service.ReviewService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = ["http://localhost:8081"])
class ReviewController(val reviewService: ReviewService, val customerService: CustomerService) {

    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun allReviews(@RequestParam(required = false) direction: Direction?) : Flux<ReviewInfoDto> {
        return reviewService.getAll(direction)
    }
    @RequestMapping(value = ["/"], method = [RequestMethod.POST])
    fun addReview(@Valid @RequestBody reviewInfoDto: ReviewInfoDto) : Mono<ResponseEntity<String>> {
        return customerService.getByEmail(reviewInfoDto.email)
            .flatMap { customer ->
                reviewService.canReview(customer)
                    .flatMap {
                        val review = Review(customer.id, customer.id, reviewInfoDto.rate, reviewInfoDto.feedback)
                        reviewService.save(review)
                            .map { ResponseEntity.ok().body("Added") }
                    }
                    .onErrorResume(ReviewExistException::class.java) {
                        Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, it.message, it))
                    }
            }
    }

    @RequestMapping(value = ["/{reviewId}"], method = [RequestMethod.DELETE])
    fun deleteReview(@PathVariable reviewId: Int) : Mono<ResponseEntity<String>> {
        reviewService.deleteById(reviewId).subscribe{
            println("Deleted")
        }
        return Mono.just(ResponseEntity.ok().build())
    }

    @RequestMapping(value = ["/"], method = [RequestMethod.PUT])
    fun updateReview(@Valid @RequestBody reviewInfoDto: ReviewInfoDto) : Mono<ResponseEntity<String>> {
        return customerService.getById(reviewInfoDto.id)
            .flatMap { customer ->
                reviewService.canUpdate(customer)
                    .flatMap { res ->
                        if (res) {
                            reviewService.updateRateAndFeedback(customer.id, reviewInfoDto.rate, reviewInfoDto.feedback).subscribe()
                            return@flatMap Mono.just(ResponseEntity.ok("Updated"))
                        } else {
                            return@flatMap Mono.just(ResponseEntity.badRequest().build())
                        }
                    }
            }
    }
}