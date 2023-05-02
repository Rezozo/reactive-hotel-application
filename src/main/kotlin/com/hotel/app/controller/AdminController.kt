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

    @RequestMapping(value = ["/users/del"], method = [RequestMethod.DELETE])
    fun deleteUser(@RequestParam id: Int) : Mono<Void> {
        return usersService.deleteById(id)
    }

    @RequestMapping(value = ["/users/update"], method = [RequestMethod.PUT])
    fun updateUser(@RequestParam id: Int) : Mono<Users> {
        return usersService.updateUsersGroup(id, Role.Manager)
    }
}