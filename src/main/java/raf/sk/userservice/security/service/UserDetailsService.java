package raf.sk.userservice.security.service;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import raf.sk.userservice.security.model.CustomUserDetails;
import raf.sk.userservice.security.jwt.JwtUtils;
import java.util.Collections;

@AllArgsConstructor
@Service
public class UserDetailsService {

    private JwtUtils jwtUtils;

    public UserDetails getUserDetails(String token){
        Claims claims = jwtUtils.getTokenClaims(token);

        Long id = Long.valueOf(String.valueOf(claims.get("id")));
        String role = claims.get("role").toString();

        return new CustomUserDetails("NOT_DEFINED", "NOT_DEFINED", Collections.singleton(roleToAuthority(role)), id);
    }

    private GrantedAuthority roleToAuthority(String role){
        return new SimpleGrantedAuthority(role);
    }
}
