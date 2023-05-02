package com.hotel.app.repository;

import com.hotel.app.models.RoomType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoomTypeRepository extends R2dbcRepository<RoomType, Integer> {
    Mono<RoomType> findByTitle(String title);
}
