package com.hotel.app.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.app.dto.BookingInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaProducerService {
    private KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public void sendMessage(BookingInfoDto bookingInfoDto) throws JsonProcessingException {
        String bookingInfoDtoJson = objectMapper.writeValueAsString(bookingInfoDto);
        kafkaTemplate.send("myTopic", bookingInfoDtoJson);
    }
}
