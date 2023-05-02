package com.hotel.app.repository;

import com.hotel.app.models.Users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRepository extends R2dbcRepository<Users, Integer> {
    Mono<Users> findByEmail(String email);

    Mono<Void> deleteByEmail(String email);
}
