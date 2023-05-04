package com.hotel.app.service.impl

import com.hotel.app.service.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono
import java.security.Key
import java.time.Instant
import java.time.Period
import java.util.*
import java.util.function.Function

class JwtServiceImpl : JwtService {

    @Value("\${hotel.secret-key}")
    private lateinit var SECRET_KEY: String

    @Value("\${hotel.token.expiration}")
    private val tokenExpiration: Long = 0

    @Value("\${hotel.refreshtoken.expiration}")
    private lateinit var refreshTokenExpiration: String

    override fun extractUserLogin(token: String): String {
        return extractClaim(token, { obj: Claims? -> obj!!.subject })
    }

    override fun <T> extractClaim(token: String?, claimsResolver: Function<Claims?, T>?): T {
        val claims = extractAllClaims(token!!)
        return claimsResolver!!.apply(claims)
    }

    override fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    override fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + tokenExpiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    override fun generateTokenUseRefreshToken(refreshToken: String): Mono<String> {
        val claims : Claims = extractAllClaims(refreshToken)
        val username : String = claims.subject
        val extraClaims: Map<String, Any> = HashMap(claims)

        return Mono.just(Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + tokenExpiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact())
    }

    override fun refreshToken(token: String): String {
        val claims : Claims = extractAllClaims(token)
        val username = claims.subject
        val extraClaims: Map<String, Any> = HashMap(claims)

        val now : Instant = Instant.now()
        val newExpirationInstant : Instant = now.plus(Period.parse(refreshTokenExpiration))
        val newExpiration : Date = Date.from(newExpirationInstant)

        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(newExpiration)
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        var newToken = token
        val username = extractUserLogin(token)
        if (username != userDetails.username) {
            return false
        }
        if (isTokenExpired(token)) {
            newToken = refreshToken(token)
        }
        return !isTokenExpired(newToken)
    }

    override fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    override fun extractExpiration(token: String): Date {
        return extractClaim(token) { obj: Claims? -> obj!!.expiration }
    }

    override fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    override fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}