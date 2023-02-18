package raf.sk.userservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.user.UserCreateDto;
import raf.sk.userservice.dto.user.UserPresentDto;
import raf.sk.userservice.model.UserEntity;

@Component
@AllArgsConstructor
public class UserMapper {

    private PasswordEncoder passwordEncoder;

    public UserPresentDto userToUserPresentDto(UserEntity userEntity){
        UserPresentDto userPresentDto = new UserPresentDto();
        userPresentDto.setName(userEntity.getName());
        userPresentDto.setLastname(userEntity.getLastname());
        userPresentDto.setEmail(userEntity.getEmail());
        userPresentDto.setDateOfBirth(userEntity.getDateOfBirth());
        userPresentDto.setPhoneNumber(userEntity.getPhoneNumber());
        userPresentDto.setNumberOfRentDays(userEntity.getNumberOfRentDays());
        return userPresentDto;
    }

    public UserEntity userCreateDtoToUser(UserCreateDto userCreateDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userCreateDto.getName());
        userEntity.setLastname(userCreateDto.getLastname());
        userEntity.setUsername(userCreateDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        userEntity.setDateOfBirth(userCreateDto.getDateOfBirth());
        userEntity.setPhoneNumber(userCreateDto.getPhoneNumber());
        userEntity.setNumberOfRentDays(0);
        return userEntity;
    }
}
