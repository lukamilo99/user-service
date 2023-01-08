package raf.sk.userservice.security.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import raf.sk.userservice.security.service.TokenService;
@Service
public class TokenServiceImplementation implements TokenService {
    @Value("${oauth.jwt.secret}")
    private String jwtSecret;
    @Override
    public String generateToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    @Override
    public Claims parseToken(String jwt) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return claims;
    }
}
