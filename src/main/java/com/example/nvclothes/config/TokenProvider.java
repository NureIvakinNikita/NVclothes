package com.example.nvclothes.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class TokenProvider {

    private static final String SECRET = System.getenv("MY_SECRET");

    public String createToken(Long id,String email){
        return Jwts.builder()
                .setSubject(id.toString())
                .claim("email", email)
                .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
                .setExpiration(Date.from(Instant.ofEpochSecond(4622478422L)))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(SECRET)
                )
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.decode(SECRET))
                .parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    public String getUserEmailFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.decode(SECRET))
                .parseClaimsJws(token).getBody();
        return (String) claims.get("email");
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(TextCodec.BASE64.decode(SECRET))
                    .parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }
}
