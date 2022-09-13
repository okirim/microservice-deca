package com.zema.commons.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class SecurityUtils {


    public static Boolean hasTokenExpired(String token) {
        Claims claims = getTokenClaims(token);
        Date expireAt = claims.getExpiration();
        return expireAt.before(new Date());
    }

    public static Claims getTokenClaims(String token) {
        return Jwts.parserBuilder()  // (1)
                .setSigningKey(getKey())         // (2)
                .build()                    // (3)
                .parseClaimsJws(token).getBody();
    }

    public static String generateToken(Long userId, Long expireAfter) {
        String id=userId.toString();
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(new Date(System.currentTimeMillis() + expireAfter))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public static SecretKey getKey() {
        return Keys.hmacShaKeyFor(SecurityConstants.SECURITY_KEY.getBytes());
    }

}