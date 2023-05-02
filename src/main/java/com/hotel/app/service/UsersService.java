package com.hotel.app.service;

import com.hotel.app.enums.Role;
import com.hotel.app.models.Users;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsersService {
    Mono<Users> getByEmail(String email);
    Flux<Users> getAll();
    Mono<Users> save(Users users);
    Mono<Void> deleteByEmail(String email);
    Mono<Void> deleteById(Integer id);
    Mono<Users> updateById(Integer id, String email, String fullName);
    Mono<Users> updateByEmail(String email, String fullName, String password);
    Mono<Users> updateUsersGroup(Integer id, Role groups);
}
