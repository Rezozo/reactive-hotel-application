package com.hotel.app.service

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono
import java.security.Key
import java.util.*
import java.util.function.Function

interface JwtService {
    fun extractUserLogin(token: String): Mono<String>
    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims?, T>?): T
    fun generateToken(userDetails: UserDetails): Mono<String>
    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails) : Mono<String>
    fun generateTokenUseRefreshToken(refreshToken: String) : Mono<String>
    fun refreshToken(token: String) : Mono<String>
    fun isTokenValid(token: String, userDetails: UserDetails) : Mono<Boolean>
    fun isTokenExpired(token: String) : Mono<Boolean>
    fun extractExpiration(token: String) : Mono<Date>
    fun extractAllClaims(token: String) : Claims
    fun getSignInKey() : Key
}