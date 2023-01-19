package raf.sk.userservice.security;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.security.service.TokenService;
import raf.sk.userservice.security.service.annotation.CheckPrivilege;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class SecurityAspect {
    @Value("${oauth.jwt.secret}")
    private String jwtSecret;
    private TokenService tokenService;
    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Around("@annotation(raf.sk.userservice.security.service.annotation.CheckPrivilege)")
    public Object aroundChechPrivilege(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String token = null;
        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if (methodSignature.getParameterNames()[i].equals("authorization")) {
                if (joinPoint.getArgs()[i].toString().startsWith("Bearer")) {
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                }
            }
        }
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Claims claims = tokenService.parseToken(token);
        if (claims == null || claims.get("role", String.class).equals("BANNED")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        CheckPrivilege checkSecurity = method.getAnnotation(CheckPrivilege.class);
        String role = claims.get("role", String.class);
        if (Arrays.asList(checkSecurity.roles()).contains(role)) {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Around(value = "@annotation(raf.sk.userservice.security.service.annotation.BanStop)")
    public Object aroundBanStop(ProceedingJoinPoint joinPoint){

        ResponseEntity<TokenResponseDto> jwt;

        try {
            jwt = (ResponseEntity<TokenResponseDto>) joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (jwt == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Claims claims = tokenService.parseToken(jwt.getBody().getToken());

        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String role = claims.get("role", String.class);

        if (role.equals("BANNED")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else{
            return jwt;
        }
    }
}
