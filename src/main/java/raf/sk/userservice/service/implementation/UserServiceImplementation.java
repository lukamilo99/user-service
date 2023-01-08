package raf.sk.userservice.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.userservice.dto.TokenRequestDto;
import raf.sk.userservice.dto.TokenResponseDto;
import raf.sk.userservice.model.User;
import raf.sk.userservice.repository.UserRepository;
import raf.sk.userservice.security.service.TokenService;
import raf.sk.userservice.service.UserService;

import java.util.Optional;
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private TokenService tokenService;
    @Override
    public void register(User user) {
        userRepository.save(user);
    }
    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        String username = tokenRequestDto.getUsername();
        String password = tokenRequestDto.getPassword();
        User user = userRepository.findByUsernameAndPassword(username, password).orElse(null); //bice exception ovde

        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getType());
        return new TokenResponseDto(tokenService.generateToken(claims));

    }
    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
