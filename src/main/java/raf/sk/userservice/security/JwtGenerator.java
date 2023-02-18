package raf.sk.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import raf.sk.userservice.model.UserEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtGenerator {
    @Value("oauth.jwt.secret")
    private String jwtSecret;

    public String generate(Authentication authentication){
        CustomUserDetails userEntity = (CustomUserDetails) authentication.getPrincipal();

        Long userId = userEntity.getId();
        String username = userEntity.getUsername();
        Date startDate = new Date();
        Date expireDate = new Date(startDate.getTime() + 1200000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("username", username);

        String token = Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(startDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("username").toString();
    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return (Long) claims.get("id");
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);

            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new AuthenticationCredentialsNotFoundException("Incorrect credentials or expired token");
        }
    }
}
