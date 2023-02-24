package com.servidor.Practica4.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${tokenSecret}")
    String tokenSecret;

    @Value("${expTokenTime}")
    int expTokenTime;

    public String newToken(String user) {
        return JWT.create()
                .withSubject(user)
                .withExpiresAt(new Date(System.currentTimeMillis() + expTokenTime))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
    }

    public String getUserFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getSubject();
    }
}
