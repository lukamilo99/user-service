package raf.sk.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserRequestDto;
import raf.sk.userservice.dto.user.UserResponseDto;
import raf.sk.userservice.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/auth/register-manager")
    public ResponseEntity<Void> registerManager(@RequestBody UserRequestDto userRequestDto){
        userService.registerManager(userRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth/register-client")
    public ResponseEntity<Void> registerClient(@RequestBody UserRequestDto userRequestDto){
        userService.registerClient(userRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/auth/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @PutMapping("/ban/{id}")
    public ResponseEntity<Void> banUserById(@PathVariable Long id){
        userService.banUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/unban/{id}")
    public ResponseEntity<Void> unbanUserById(@PathVariable Long id){
        userService.unbanUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUserById(@RequestBody UserRequestDto dto){
        userService.updateUserById(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
