package com.hotel.app.controller

import com.hotel.app.enums.Role
import com.hotel.app.models.Users
import com.hotel.app.service.UsersService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:8081/")
class AdminController(val usersService: UsersService){
    @RequestMapping(value = ["/users"], method = [RequestMethod.GET])
    fun getUsers() : Flux<Users> {
        return usersService.all;
    }

    @RequestMapping(value = ["/users/{userId}"], method = [RequestMethod.DELETE])
    fun deleteUser(@PathVariable userId: Int) : Mono<Void> {
        return usersService.deleteById(userId)
    }

    @RequestMapping(value = ["/users/roles/{userId}"], method = [RequestMethod.PUT])
    fun updateUser(@PathVariable userId: Int) : Mono<Users> {
        return usersService.updateUsersGroup(userId, Role.Manager)
    }
}