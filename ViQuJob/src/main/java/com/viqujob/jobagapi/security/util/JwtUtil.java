package com.viqujob.jobagapi.security.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.viqujob.jobagapi.exception.ResourceNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 10 * 60 * 60;
    private String secret = "Serial";

    public String generateToken(String name, Long number, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        Map<String, String> payload = new HashMap<>();
        payload.put("sub", name);
        payload.put("number", number.toString());

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0").withHeader(claims).withPayload(payload)
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new ResourceNotFoundException("");
        }
    }

    public DecodedJWT getAllClaimsFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception) {
            throw new ResourceNotFoundException("");
        }
    }

    public String getUsernameFromClaim(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Boolean getExpirationFromClaim(String token) {
        final Date expiration = getAllClaimsFromToken(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public Boolean validToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromClaim(token);
        return (username.equals(userDetails.getUsername()) && !getExpirationFromClaim(token));
    }
}
