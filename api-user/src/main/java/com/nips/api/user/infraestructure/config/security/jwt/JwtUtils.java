package com.nips.api.user.infraestructure.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwt.expiration.access-token}")
    private long expirationAccessToken;

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.issuer}")
    private String issuer;

    private Algorithm algorithm;

    private Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(secretKey);
        }
        return algorithm;
    }

    public String createToken(UserDetails userDetails) {
        var tokenBuilder = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationAccessToken));

        return tokenBuilder.sign(getAlgorithm());
    }

    public boolean verifyToken(String jwt) throws JWTVerificationException{
        try {
            JWT.require(getAlgorithm())
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUserToken(String token) {
        return JWT.decode(token).getSubject();
    }
}
