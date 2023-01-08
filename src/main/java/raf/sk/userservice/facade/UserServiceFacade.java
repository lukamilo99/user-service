package raf.sk.userservice.facade;

import raf.sk.userservice.dto.TokenRequestDto;
import raf.sk.userservice.dto.TokenResponseDto;
import raf.sk.userservice.dto.UserCreateDto;

public interface UserServiceFacade {

    void registerClient(UserCreateDto user);
    void registerManager(UserCreateDto user);
    TokenResponseDto login(TokenRequestDto token);
    void banUserById(Long id);
}
