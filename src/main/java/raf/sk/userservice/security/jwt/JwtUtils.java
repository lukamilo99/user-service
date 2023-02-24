package raf.sk.userservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.auth.UserDetailsDto;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    public String generateToken(UserDetailsDto userDetailsDto){

        Date startDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(startDate.getTime() + 1200000);

        byte[] decodedSecret = Base64.getDecoder().decode(jwtSecret.getBytes());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetailsDto.getId());
        claims.put("role", userDetailsDto.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(startDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, decodedSecret)
                .compact();
    }

    public Claims getTokenClaims(String token) {

        byte[] decodedSecret = Base64.getDecoder().decode(jwtSecret.getBytes());

        try {
            return Jwts.parser()
                    .setSigningKey(decodedSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
