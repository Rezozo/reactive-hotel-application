package com.hotel.app.repository;

import com.hotel.app.dto.ReviewInfoDto;
import com.hotel.app.models.Review;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReviewRepository extends R2dbcRepository<Review, Integer> {
    @Query(value = "INSERT INTO review (id, customer_id, rate, feedback) VALUES (:id, :customerId, :rate, :feedback)")
    Mono<Review> insert(@Param("id") Integer id, @Param("customerId") Integer customer, @Param("rate") Byte rate, @Param("feedback") String feedback);
    @Query(value = "Select r.id, c.full_name, c.email, r.rate, r.feedback " +
            "from Review r, Customer c where c.id = r.customer_id and c.email = :email")
    Mono<ReviewInfoDto> findReviewInfoOneByEmail(String email);
    @Query(value = "Select r.id, c.full_name, c.email, r.rate, r.feedback " +
            "from Review r, Customer c where c.id = r.customer_id and c.id = :id")
    Mono<ReviewInfoDto> findReviewInfoOneById(Integer id);
    @Query(value = "Select r.id, c.full_name, c.email, r.rate, r.feedback " +
            "from Review r, Customer c where c.id = r.customer_id and c.phone_number = :phoneNumber")
    Mono<ReviewInfoDto> findReviewInfoOneByPhone(String phoneNumber);
    @Query(value = "Select r.id, c.full_name, c.email, r.rate, r.feedback " +
            "from Review r, Customer c where c.id = r.customer_id")
    Flux<ReviewInfoDto> findReviewInfoAll();
    @Query("Select r.id, c.full_name, c.email, r.rate, r.feedback " +
            "FROM Review r, Customer c WHERE c.id = r.customer_id " +
            "ORDER BY CASE WHEN :direction = 'ASC' THEN r.rate END ASC, " +
            "CASE WHEN :direction = 'DESC' THEN r.rate END DESC")
    Flux<ReviewInfoDto> findReviewInfoAllOrderByRateDesc(String direction);
}
