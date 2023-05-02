package com.hotel.app.config;

import com.hotel.app.service.impl.JwtServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtServiceImpl jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        Mono<String> username = jwtService.extractUserLogin(authToken);

        return SecurityContextHolder.getContext().getAuthentication() == null
                ? username.flatMap(name -> {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(name);
            return jwtService.isTokenValid(authToken, userDetails)
                    .flatMap(valid -> {
                        if (valid) {
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            return Mono.just(authenticationToken);
                        } else {
                            return Mono.empty();
                        }
                    });
        }) : Mono.empty();
    }
}
