package raf.sk.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserCreateDto;
import raf.sk.userservice.dto.user.UserPresentDto;
import raf.sk.userservice.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/auth/register-client")
    public ResponseEntity<Void> registerClient(@RequestBody UserCreateDto userCreateDto){
        userService.registerClient(userCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth/register-manager")
    public ResponseEntity<Void> registerManager(@RequestBody UserCreateDto userCreateDto){
        userService.registerManager(userCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/auth/login")
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
    public ResponseEntity<UserPresentDto> findUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Void> updateUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestBody UserCreateDto dto){
        userService.updateUserById(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateReservationDays/{id}")
    public ResponseEntity<Void> updateReservationDays(@PathVariable Long id, @RequestParam int numOfDays){
        userService.updateUserReservationDays(id, numOfDays);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
