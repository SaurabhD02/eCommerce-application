package com.flipkart.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Configuration
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignedKey()).build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        return date.before(new Date());
    }

    private SecretKey getSignedKey() {
        byte[] seckey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(seckey);
    }

}
