package com.hotel.app.service.impl

import com.hotel.app.models.Token
import com.hotel.app.repository.TokenRepository
import com.hotel.app.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

class TokenServiceImpl constructor(private var tokenRepository: TokenRepository): TokenService{
    override fun getByRefreshToken(refreshToken: String): Mono<Token> {
        return tokenRepository.findByRefreshToken(refreshToken)
            .switchIfEmpty(Mono.empty())
    }

    override fun getByToken(token: String): Mono<Token> {
        return tokenRepository.findByToken(token)
            .switchIfEmpty(Mono.empty())
    }

    override fun updateToken(token: String, refreshToken: String): Mono<Token> {
        return tokenRepository.findByRefreshToken(refreshToken)
            .switchIfEmpty(Mono.empty()).flatMap { jwt ->
                jwt.token = token
                tokenRepository.save(jwt)
            }
    }

    override fun save(token: Token): Mono<Token> {
        return tokenRepository.save(token)
    }
}