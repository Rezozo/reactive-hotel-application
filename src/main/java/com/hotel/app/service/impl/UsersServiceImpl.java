package com.hotel.app.service.impl;

import com.hotel.app.enums.Role;
import com.hotel.app.models.Users;
import com.hotel.app.repository.UsersRepository;
import com.hotel.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public Mono<Users> getByEmail(String email) {
        return usersRepository.findByEmail(email).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<Users> getAll() {
        return usersRepository.findAll();
    }

    @Override
    public Mono<Users> save(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Mono<Void> deleteByEmail(String email) {
        return usersRepository.deleteByEmail(email);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return usersRepository.deleteById(id);
    }

    @Override
    public Mono<Users> updateById(Integer id, String email, String fullName) {
        return usersRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(users -> {
                    users.setEmail(email);
                    users.setFullName(fullName);
                    return usersRepository.save(users);
                });
    }

    @Override
    public Mono<Users> updateByEmail(String email, String fullName, String password) {
        return usersRepository.findByEmail(email)
                .switchIfEmpty(Mono.empty())
                .flatMap(users -> {
                    users.setFullName(fullName);
                    users.setEmail(email);
                    users.setPassword(password);
                    return usersRepository.save(users);
                });
    }

    @Override
    public Mono<Users> updateUsersGroup(Integer id, Role groups) {
        return usersRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(users -> {
                    users.setGroups(groups);
                    return usersRepository.save(users);
                });
    }
}
