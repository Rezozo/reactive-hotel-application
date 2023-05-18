package com.hotel.app.controller;

import com.hotel.app.config.request.AuthenticationRequest;
import com.hotel.app.config.request.RegisterRequest;
import com.hotel.app.config.response.AuthenticationResponse;
import com.hotel.app.service.JwtService;
import com.hotel.app.service.TokenService;
import com.hotel.app.service.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:8081/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl service;
    private final JwtService jwtService;
    private final TokenService tokenService;
    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public Mono<ResponseEntity<Void>> register(@Valid @RequestBody RegisterRequest request)
    {
        return service.register(request)
                .map(reg -> ResponseEntity.ok().<Void>build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }
    @RequestMapping(value = "/authenticate", method = {RequestMethod.GET, RequestMethod.POST})
    public Mono<ResponseEntity<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request)
    {
        Mono<AuthenticationResponse> authenticationResponse = service.authenticate(request);
        return authenticationResponse
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public Mono<ResponseEntity<AuthenticationResponse>> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        return tokenService.getByRefreshToken(refreshToken)
                .flatMap(token -> Mono.justOrEmpty(token)
                        .flatMap(t -> jwtService.generateTokenUseRefreshToken(refreshToken)
                                .flatMap(newToken ->
                                        tokenService.updateToken(newToken, refreshToken)
                                                .thenReturn(ResponseEntity.ok(new AuthenticationResponse(newToken))))
                        )
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)))
                );
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> logout (){
        return Mono.just(ResponseEntity.ok().body("Logout"));
    }
}
