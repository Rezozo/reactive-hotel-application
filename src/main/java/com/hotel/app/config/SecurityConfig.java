package com.hotel.app.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final ReactiveAuthenticationManager authenticationManager;
    private final SecurityContext securityContext;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContext)
                .authorizeExchange()
                .pathMatchers("/reviews/").permitAll()
                .pathMatchers("/auth/refresh").permitAll()
                .pathMatchers("/reviews/**").authenticated()
                .pathMatchers("/booking/").authenticated()
                .pathMatchers("/profile/**").authenticated()
                .pathMatchers("/manager/**").hasAnyAuthority("Manager", "Admin")
                .pathMatchers("/admin/**").hasAuthority("Admin")
                .anyExchange().permitAll()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .and()
                .build();
    }
}
