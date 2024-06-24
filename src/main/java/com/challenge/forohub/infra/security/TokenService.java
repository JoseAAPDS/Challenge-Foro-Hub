package com.challenge.forohub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.challenge.forohub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${forohub.security.secret}")
    private String forohubSecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(forohubSecret);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("roles", usuario.getPerfil().name())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token){
        if (token == null) {
            throw new RuntimeException();
        }

        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(forohubSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build();
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("invalid token");
        }
        if (decodedJWT.getSubject() == null){
            throw new RuntimeException("verifier invalid");
        }
        return decodedJWT.getSubject();
    }

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

}
