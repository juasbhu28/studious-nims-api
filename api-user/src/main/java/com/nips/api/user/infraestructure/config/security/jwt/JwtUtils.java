package com.nips.api.user.infraestructure.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
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

    public String getUsernameJWT(String jwt) {
        return JWT.require(getAlgorithm())
                .build()
                .verify(jwt)
                .getSubject();
    }

    public String getUsername(String jwt) {
        jwt = jwt.trim();
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(jwt.split("\\.")[1]);
            return  getUserEmailFromToken(new String(decodedBytes));
        } catch (IllegalArgumentException e) {
            return "Error al decodificar el token: Caracter Base64 ilegal. " + e.getMessage();
        }
    }

    private String getUserEmailFromToken(String token) {
        String emailKey = "\"sub\":\"";
        int startIndex = token.indexOf(emailKey) + emailKey.length();
        int endIndex = token.indexOf("\"", startIndex);

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return token.substring(startIndex, endIndex);
        } else {
            return "Error en el token";
        }
    }
}
