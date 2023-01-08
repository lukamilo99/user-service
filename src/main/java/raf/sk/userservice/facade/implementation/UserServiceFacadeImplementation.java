package raf.sk.userservice.facade.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.userservice.dto.TokenRequestDto;
import raf.sk.userservice.dto.TokenResponseDto;
import raf.sk.userservice.dto.UserCreateDto;
import raf.sk.userservice.facade.UserServiceFacade;
import raf.sk.userservice.mapper.UserMapper;
import raf.sk.userservice.model.User;
import raf.sk.userservice.service.implementation.RankServiceImplementation;
import raf.sk.userservice.service.implementation.RoleServiceImplementation;
import raf.sk.userservice.service.implementation.UserServiceImplementation;

@Service
@AllArgsConstructor
public class UserServiceFacadeImplementation implements UserServiceFacade {
    private UserServiceImplementation userService;
    private RoleServiceImplementation roleService;
    private UserMapper userMapper;

    @Override
    public void registerClient(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        roleService.findRoleByType("CLIENT").ifPresent(user::setRole);
        userService.register(user);
    }
    @Override
    public void registerManager(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        roleService.findRoleByType("MANAGER").ifPresent(user::setRole);
        userService.register(user);
    }
    @Override
    public TokenResponseDto login(TokenRequestDto token) {
        return userService.login(token);
    }

    @Override
    public void banUserById(Long id) {
        User user = userService.findUserById(id).orElse(null);
        if (user != null) {
            user.setRole(roleService.findRoleByType("BANNED").get());
            userService.register(user);
        }
    }
}
