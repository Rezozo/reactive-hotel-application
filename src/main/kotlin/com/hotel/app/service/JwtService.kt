package com.hotel.app.service

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono
import java.security.Key
import java.util.*
import java.util.function.Function

interface JwtService {
    fun extractUserLogin(token: String): String
    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims?, T>?): T
    fun generateToken(userDetails: UserDetails): String
    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails) : String
    fun generateTokenUseRefreshToken(refreshToken: String) : Mono<String>
    fun refreshToken(token: String) : String
    fun isTokenValid(token: String, userDetails: UserDetails) : Mono<Boolean>
    fun isTokenExpired(token: String) : Boolean
    fun extractExpiration(token: String) : Date
    fun extractAllClaims(token: String) : Claims
    fun getSignInKey() : Key
}