package com.hotel.app.repository;

import com.hotel.app.models.Token;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TokenRepository extends R2dbcRepository<Token, Integer> {
    Mono<Token> findByRefreshToken(String refreshToken);
    Mono<Token> findByToken(String token);
}
