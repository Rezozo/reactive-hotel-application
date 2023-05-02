package com.hotel.app.repository;

import com.hotel.app.dto.RoomInfoDto;
import com.hotel.app.models.Room;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RoomRepository extends R2dbcRepository<Room, Integer> {
    Mono<Room> findByTitle(String title);

    @Query("SELECT r.id, t.type_title, r.room_number, r.title, r.description, r.image_path, r.price, r.room_status " +
            "FROM room r " +
            "Join type t on r.type_id = t.id " +
            "Where (:status IS NULL OR r.room_status = :status) " +
            "Order by r.id ASC")
    Flux<RoomInfoDto> findRoomInfoAllByStatus(Boolean status);

    @Query("SELECT r.id, t.type_title, r.room_number, r.title, r.description, r.image_path, r.price, r.room_status " +
            "FROM room r " +
            "Join type t on r.type_id = t.id " +
            "WHERE (:status IS NULL OR r.room_status = :status) " +
            "ORDER BY CASE WHEN :direction = 'ASC' THEN r.price END ASC, " +
            "CASE WHEN :direction = 'DESC' THEN r.price END DESC")
    Flux<RoomInfoDto> findRoomInfoAllByStatusOrderByPrice(Boolean status, String direction);

    @Query("SELECT r.id, t.type_title, r.room_number, r.title, r.description, r.image_path, r.price, r.room_status " +
            "FROM room r " +
            "Join type t on r.type_id = t.id " +
            "where r.title = :roomTitle and t.type_title = :type_title")
    Mono<RoomInfoDto> findOneRoomByTypeAndTitle(String type_title, String roomTitle);

    @Query("SELECT r.id, t.type_title, r.room_number, r.title, r.description, r.image_path, r.price, r.room_status " +
            "FROM room r " +
            "Join type t on r.type_id = t.id " +
            "WHERE t.type_title = :title and (:status IS NULL OR r.room_status = :status) " +
            "Order by r.id ASC")
    Flux<RoomInfoDto> findRoomByTypeAndStatus(@Param("title") String type_title, @Param("status") Boolean status);

    @Query("SELECT r.id, t.type_title, r.room_number, r.title, r.description, r.image_path, r.price, r.room_status " +
            "FROM room r " +
            "Join type t on r.type_id = t.id " +
            "WHERE t.type_title = :title and (:status IS NULL OR r.room_status = :status) " +
            "ORDER BY CASE WHEN :direction = 'ASC' THEN r.price END ASC, " +
            "CASE WHEN :direction = 'DESC' THEN r.price END DESC")
    Flux<RoomInfoDto> findRoomByTypeAndStatusOrderByPrice(@Param("title") String type_title, @Param("status") Boolean status, @Param("direction") String direction);
}
