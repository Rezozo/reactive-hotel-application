package com.hotel.app.service.impl

import com.hotel.app.config.request.AuthenticationRequest
import com.hotel.app.config.request.RegisterRequest
import com.hotel.app.config.response.AuthenticationResponse
import com.hotel.app.enums.Role
import com.hotel.app.models.Customer
import com.hotel.app.models.Users
import com.hotel.app.service.CustomerService
import com.hotel.app.service.JwtService
import com.hotel.app.service.UsersService
import org.apache.http.auth.InvalidCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono

class AuthenticationServiceImpl(private val usersService: UsersService,
                                private val passwordEncoder: PasswordEncoder,
                                private val jwtService: JwtService,
                                private val customerService: CustomerService
) {
    fun register(request: RegisterRequest) : Mono<Customer> {
        val user = Users(
            request.fullName,
            Role.User,
            request.email,
            request.password,
        )

        usersService.save(user).subscribe {
            println("Saved")
        }

        return usersService.getByEmail(request.email)
            .flatMap { users ->
                val customer = Customer(
                    request.fullName,
                    request.email,
                    request.phoneNumber
                )
                customerService.save(customer).doOnNext { println("Saved") }
            }
    }

    fun authenticate(request: AuthenticationRequest): Mono<AuthenticationResponse> {
        return usersService.getByEmail(request.email)
            .flatMap { user ->
                if (passwordEncoder.matches(request.password, user.password)) {
                    val token = jwtService.generateToken(user)
                    val refreshToken = jwtService.refreshToken(token)
                    return@flatMap Mono.just(AuthenticationResponse(
                        token, refreshToken
                    ))
                } else {
                    Mono.error(InvalidCredentialsException("Invalid email or password"))
                }
            }.switchIfEmpty(Mono.error(InvalidCredentialsException("Invalid email or password")))
    }

}