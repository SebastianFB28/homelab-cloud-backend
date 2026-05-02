package com.homelab.cloud.infrastructure.adapter.out.security;

// import port out jwt token
import com.homelab.cloud.application.port.out.JwtTokenPort;

// import domain model user
import com.homelab.cloud.domain.model.User;

// import JWT library
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

// import spring framework
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// import java util
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtAdapter implements JwtTokenPort {

    // @Value("${jwt.secret}") // inject secretkey from application properties
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                // subject: set the subject to the user's email
                .subject(user.getEmail())
                // data
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .claim("nickname", user.getNickname())
                // date create
                .issuedAt(new Date(System.currentTimeMillis()))
                // expieration date
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                // sign the token with the secret key
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }


    @Override
    public boolean validateToken(String token) {
        try {
            return !extractAllClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false; // Si explota (firma mala, expirado), es inválido
        }
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
