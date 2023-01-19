package raf.sk.userservice.service;

import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserCreateDto;
import raf.sk.userservice.dto.user.UserPresentDto;

public interface UserService {
    void registerClient(UserCreateDto dto);
    void registerManager(UserCreateDto dto);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    UserPresentDto findUserById(Long id);
    void banUserById(Long id);
    void unbanUserById(Long id);
    void deleteUserById(Long id);
    void updateUserById(Long id, UserCreateDto dto);
    void updateUserReservationDays(Long id, int days);
}
