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

        userResponseDto.setFirstname(userEntity.getName());
        userResponseDto.setLastname(userEntity.getLastname());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setPhoneNumber(userEntity.getPhoneNumber());

        return userResponseDto;
    }

    public UserEntity userRequestDtoToUser(UserRequestDto userRequestDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setName(userRequestDto.getFirstname());
        userEntity.setLastname(userRequestDto.getLastname());
        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userEntity.setDateOfBirth(userRequestDto.getDateOfBirth());
        userEntity.setPhoneNumber(userRequestDto.getPhoneNumber());
        userEntity.setNumberOfRentDays(0);
        userEntity.setEnabled(false);

        return userEntity;
    }
}
