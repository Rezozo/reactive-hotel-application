package com.hotel.app.controller

import com.hotel.app.dto.ReviewInfoDto
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
    fun allReviews(@RequestParam(required = false) direction: String?) : Flux<ReviewInfoDto> {
        return reviewService.getAll(direction)
    }
    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun addReview(@Valid @RequestBody reviewInfoDto: ReviewInfoDto) : Mono<ResponseEntity<String>> {
        return customerService.getByEmail(reviewInfoDto.email)
            .flatMap { customer ->
                reviewService.canReview(reviewInfoDto, customer)
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

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun deleteReview(@RequestBody review: Review) : Mono<ResponseEntity<String>> {
        return customerService.getById(review.id)
            .flatMap { customer ->
                reviewService.deleteById(customer.id).subscribe{
                    println("Deleted")
                }
                return@flatMap Mono.just(ResponseEntity.ok().build())
            }
    }

    @RequestMapping(value = ["/update"], method = [RequestMethod.PUT])
    fun updateReview(@Valid @RequestBody reviewInfoDto: ReviewInfoDto) : Mono<ResponseEntity<String>> {
        return customerService.getById(reviewInfoDto.id)
            .flatMap { customer ->
                reviewService.canUpdate(reviewInfoDto, customer)
                    .flatMap { res ->
                        if (res) {
                            reviewService.updateRateAndFeetback(customer.id, reviewInfoDto.rate, reviewInfoDto.feedback).subscribe()
                            return@flatMap Mono.just(ResponseEntity.ok("Updated"))
                        } else {
                            return@flatMap Mono.just(ResponseEntity.badRequest().build())
                        }
                    }
            }
    }
}