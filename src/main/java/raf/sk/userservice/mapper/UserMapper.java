package raf.sk.userservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.UserCreateDto;
import raf.sk.userservice.dto.UserPresentDto;
import raf.sk.userservice.model.User;

@Component
public class UserMapper {
    public UserPresentDto userToUserPresentDto(User user){
        UserPresentDto userPresentDto = new UserPresentDto();
        userPresentDto.setName(user.getName());
        userPresentDto.setLastname(user.getLastname());
        userPresentDto.setEmail(user.getEmail());
        userPresentDto.setDateOfBirth(user.getDateOfBirth());
        userPresentDto.setPhoneNumber(user.getPhoneNumber());
        userPresentDto.setNumberOfRentDays(user.getNumberOfRentDays());
        return userPresentDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto){
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setLastname(userCreateDto.getLastname());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setDateOfBirth(userCreateDto.getDateOfBirth());
        user.setPhoneNumber(userCreateDto.getPhoneNumber());
        user.setNumberOfRentDays(0);
        return user;
    }
}
