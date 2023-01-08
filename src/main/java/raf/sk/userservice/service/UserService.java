package raf.sk.userservice.service;

import raf.sk.userservice.dto.TokenRequestDto;
import raf.sk.userservice.dto.TokenResponseDto;
import raf.sk.userservice.model.User;
import java.util.Optional;

public interface UserService {
    void register(User user);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    Optional<User> findUserById(Long id);
}
