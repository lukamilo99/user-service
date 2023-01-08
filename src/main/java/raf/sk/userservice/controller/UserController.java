package raf.sk.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.userservice.dto.TokenRequestDto;
import raf.sk.userservice.dto.TokenResponseDto;
import raf.sk.userservice.dto.UserCreateDto;
import raf.sk.userservice.facade.implementation.UserServiceFacadeImplementation;
import raf.sk.userservice.security.service.annotation.CheckPrivilege;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserServiceFacadeImplementation userServiceFacade;
    @PostMapping("/register/client")
    public ResponseEntity<Void> registerClient(@RequestBody UserCreateDto userCreateDto){
        userServiceFacade.registerClient(userCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register/manager")
    public ResponseEntity<Void> registerManager(@RequestBody UserCreateDto userCreateDto){
        userServiceFacade.registerManager(userCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(userServiceFacade.login(tokenRequestDto), HttpStatus.OK);
    }
    @PutMapping("/ban/{id}")
    @CheckPrivilege(roles = {"ADMIN"})
    public ResponseEntity<Void> banUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        userServiceFacade.banUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
