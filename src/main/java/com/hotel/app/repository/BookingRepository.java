package com.hotel.app.repository;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Booking;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Map;

@Repository
public interface BookingRepository extends R2dbcRepository<Booking, Integer> {
    @Query(value = "Select b.id, c.full_name, c.phone_number, r.title, b.arrival_date, b.departure_date, b.total_cost " +
            "from booking b, customer c, room r where c.id = b.customer_id and r.id = b.room_id and " +
            "phone_number = :phoneNumber")
    Flux<BookingInfoDto> findBookingInfoOne(String phoneNumber);

    @Query(value = "Select b.id, c.full_name, c.phone_number, r.title, b.arrival_date, b.departure_date, b.total_cost " +
            "from booking b, customer c, room r where c.id = b.customer_id and r.id = b.room_id")
    Flux<BookingInfoDto> findBookingInfoAll();

    @Query(value = "Select b.id, c.full_name, c.phone_number, r.title, b.arrival_date, b.departure_date, b.total_cost " +
            "from booking b, customer c, room r where c.id = b.customer_id and r.id = b.room_id " +
            "ORDER BY CASE WHEN :price = 'ASC' THEN b.total_cost END ASC, " +
            "CASE WHEN :price = 'DESC' THEN b.total_cost END DESC")
    Flux<BookingInfoDto> findBookingInfoAllOrderByPrice(String price);

    @Query(value = "Select b.id, c.full_name, c.phone_number, r.title, b.arrival_date, b.departure_date, b.total_cost " +
            "from booking b, customer c, room r where c.id = b.customer_id and r.id = b.room_id " +
            "ORDER BY CASE WHEN :arrival = 'ASC' THEN b.arrival_date END ASC, " +
            "CASE WHEN :arrival = 'DESC' THEN b.arrival_date END DESC")
    Flux<BookingInfoDto> findBookingInfoAllOrderByArrival( String arrival);

    @Query(value = "SELECT booking.arrival_date " +
            "FROM booking " +
            "WHERE booking.room_id = :roomId and (booking.arrival_date > CURRENT_TIMESTAMP or booking.departure_date > CURRENT_TIMESTAMP) " +
            "Order by booking.id")
    Flux<LocalDate> findBookingArrivalByRoomId(Integer roomId);

    @Query(value = "SELECT booking.departure_date " +
            "FROM booking " +
            "WHERE booking.room_id = :roomId and (booking.arrival_date > CURRENT_TIMESTAMP or booking.departure_date > CURRENT_TIMESTAMP) " +
            "Order by booking.id, booking.arrival_date")
    Flux<LocalDate> findBookingDepartureByRoomId(Integer roomId);

    @Query(value = "Select count(id), sum(total_cost) " +
            "from booking")
    Flux<Map<String, Integer>> findStatsBooking();

    @Query(value = "Select count(id), sum(total_cost) " +
            "from booking " +
            "WHERE EXTRACT(MONTH FROM arrival_date) = EXTRACT(MONTH FROM NOW()) " +
            "AND EXTRACT(YEAR FROM arrival_date) = EXTRACT(YEAR FROM NOW())")
    Flux<Map<String, Integer>> findStatsBookingThisMonth();
}
