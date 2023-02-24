package raf.sk.userservice.service;

import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserRequestDto;
import raf.sk.userservice.dto.user.UserResponseDto;

public interface UserService {
    void registerClient(UserRequestDto dto);
    void registerManager(UserRequestDto dto);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    UserResponseDto findUserById(Long id);
    void banUserById(Long id);
    void unbanUserById(Long id);
    void deleteUserById(Long id);
    void updateUserById(UserRequestDto dto);
    void updateUserReservationDays(Long id, int days);
}
