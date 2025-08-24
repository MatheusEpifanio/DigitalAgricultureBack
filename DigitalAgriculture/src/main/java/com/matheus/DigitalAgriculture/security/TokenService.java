package com.matheus.DigitalAgriculture.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.matheus.DigitalAgriculture.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.security.token.secret}")
    private String secret;

    public String generateToken(Users users) {
        try {
            var algorithm = Algorithm.HMAC256((secret));
            return JWT.create()
                    .withIssuer("digital-agriculture")
                    .withSubject(users.getEmail())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new RuntimeException("", exception);
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256((secret));
            return JWT.require(algorithm)
                    .withIssuer("digital-agriculture")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTCreationException exception) {
            return "";
        }
    }

    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
