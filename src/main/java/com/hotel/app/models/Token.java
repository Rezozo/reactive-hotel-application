package com.hotel.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tokens")
public class Token {
    @Id
    private Integer id;
    private String token;
    @Column("refresh_token")
    private String refreshToken;

    public Token() {
    }

    public Token(Integer id, String token, String refreshToken) {
        this.id = id;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
