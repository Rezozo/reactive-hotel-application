package com.hotel.app.service.impl;

import com.hotel.app.models.RoomType;
import com.hotel.app.repository.RoomTypeRepository;
import com.hotel.app.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public Mono<RoomType> getById(Integer id) {
        return roomTypeRepository.findById(id).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<RoomType> getByTitle(String title) {
        return roomTypeRepository.findByTitle(title).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<RoomType> getAll() {
        return roomTypeRepository.findAll();
    }

    @Override
    public Mono<RoomType> save(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return roomTypeRepository.deleteById(id);
    }
}
