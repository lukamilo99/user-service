package raf.sk.userservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.user.UserRequestDto;
import raf.sk.userservice.dto.user.UserResponseDto;
import raf.sk.userservice.model.UserEntity;

@Component
@AllArgsConstructor
public class UserMapper {

    private PasswordEncoder passwordEncoder;

    public UserResponseDto userToUserPresentDto(UserEntity userEntity){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(userEntity.getName());
        userResponseDto.setLastname(userEntity.getLastname());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setDateOfBirth(userEntity.getDateOfBirth());
        userResponseDto.setPhoneNumber(userEntity.getPhoneNumber());
        userResponseDto.setNumberOfRentDays(userEntity.getNumberOfRentDays());
        return userResponseDto;
    }

    public UserEntity userRequestDtoToUser(UserRequestDto userRequestDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setName(userRequestDto.getName());
        userEntity.setLastname(userRequestDto.getLastname());
        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userEntity.setDateOfBirth(userRequestDto.getDateOfBirth());
        userEntity.setPhoneNumber(userRequestDto.getPhoneNumber());
        userEntity.setNumberOfRentDays(0);
        return userEntity;
    }
}
