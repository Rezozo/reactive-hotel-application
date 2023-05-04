package com.hotel.app.config

import com.hotel.app.repository.UsersRepository
import com.hotel.app.service.CustomerService
import com.hotel.app.service.JwtService
import com.hotel.app.service.UsersService
import com.hotel.app.service.impl.AuthenticationServiceImpl
import com.hotel.app.service.impl.JwtServiceImpl
import com.hotel.app.service.impl.UsersServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono

@Configuration
class UsersConfig {
    @Bean
    fun usersService(usersRepository: UsersRepository) : UsersServiceImpl {
        return UsersServiceImpl(usersRepository)
    }
    @Bean
    fun jwtService(): JwtServiceImpl {
        return JwtServiceImpl()
    }

    @Bean
    fun users(usersRepository: UsersRepository): ReactiveUserDetailsService {
        return ReactiveUserDetailsService  { username: String ->
            usersRepository.findByEmail(username)
                .flatMap { user ->
                    Mono.just(
                        User(
                            user.email,
                            user.password,
                            user.authorities
                        )
                    )
                }
        }
    }

    @Bean
    fun authenticationService(
        usersService: UsersService,
        passwordEncoder: PasswordEncoder,
        jwtService: JwtService,
        customerService: CustomerService
    ): AuthenticationServiceImpl {
        return AuthenticationServiceImpl(usersService, passwordEncoder, jwtService, customerService)
    }
}



