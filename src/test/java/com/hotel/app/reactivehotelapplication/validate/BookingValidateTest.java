package com.hotel.app.reactivehotelapplication.validate;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.exceptions.DatePastException;
import com.hotel.app.exceptions.RoomOccupiedException;
import com.hotel.app.models.Customer;
import com.hotel.app.models.Room;
import com.hotel.app.service.BookingService;
import com.hotel.app.validate.BookingValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingValidateTest {
    @InjectMocks
    BookingValidate bookingValidate;
    @Mock
    BookingService bookingService;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(bookingValidate, "maxDays", "30");
    }
    @Test
    public void validBooking_Success() {
        LocalDate arrival = LocalDate.parse("2023-05-20");
        LocalDate departure = LocalDate.parse("2023-05-25");
        BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                "79239519735", "Luxury one", arrival, departure, null);

        Room room = new Room(1,1,100,"Luxury one", "Best room", "../img/LuxuryOne.png", 200, true);

        when(bookingService.getArrivalDates(eq(room.getId()))).thenReturn(Flux.just(arrival.plusWeeks(2)));
        when(bookingService.getDepartureDates(eq(room.getId()))).thenReturn(Flux.just(departure.plusWeeks(5)));
        when(bookingService.canBook(bookingInfoDto, bookingService.getArrivalDates(room.getId()), bookingService.getDepartureDates(room.getId()))).thenReturn(Mono.just(true));

        Mono<Boolean> result = bookingValidate.validBooking(bookingInfoDto, room);

        StepVerifier.create(result)
                .expectNext(true)
                .expectComplete()
                .verify();
    }

    @Test
    public void validBooking_Failed() {

        LocalDate arrival = LocalDate.parse("2023-04-15");
        LocalDate departure = LocalDate.parse("2023-04-25");
        BookingInfoDto bookingInfoDto = new BookingInfoDto(null, "Flerova Oksana Antonovna",
                "79239519735", "Luxury one", arrival, departure, null);

        Customer customer = new Customer(1, "Flerova Oksana Antonovna","oksana96@yandex.ru", "79239519735");

        Room room = new Room(1,1,100,"Luxury one", "Best room", "../img/LuxuryOne.png", 200, true);

        when(bookingService.getArrivalDates(eq(room.getId()))).thenReturn(Flux.just(arrival));
        when(bookingService.getDepartureDates(eq(room.getId()))).thenReturn(Flux.just(departure.plusWeeks(1)));
        when(bookingService.canBook(bookingInfoDto, bookingService.getArrivalDates(room.getId()), bookingService.getDepartureDates(room.getId()))).thenReturn(Mono.just(true));

        StepVerifier.create(bookingValidate.validBooking(bookingInfoDto, room))
                .expectError(DatePastException.class)
                .verify();

    }
}

