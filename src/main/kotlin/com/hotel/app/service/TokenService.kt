package com.hotel.app.service

import com.hotel.app.models.Token
import reactor.core.publisher.Mono

interface TokenService {
    fun getByRefreshToken(refreshToken: String) : Mono<Token>
    fun getByToken(token: String) : Mono<Token>
    fun updateToken(token: String, refreshToken: String) : Mono<Token>
    fun save(token: Token) : Mono<Token>
}