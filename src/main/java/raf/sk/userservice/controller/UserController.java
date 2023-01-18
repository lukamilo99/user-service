package raf.sk.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserCreateDto;
import raf.sk.userservice.dto.user.UserPresentDto;
import raf.sk.userservice.security.service.annotation.CheckPrivilege;
import raf.sk.userservice.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/register/client")
    public ResponseEntity<Void> registerClient(@RequestBody UserCreateDto userCreateDto){
        userService.register(userCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register/manager")
    public ResponseEntity<Void> registerManager(@RequestBody UserCreateDto userCreateDto){
        userService.register(userCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto){
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }
    @PutMapping("/ban/{id}")
    @CheckPrivilege(roles = {"ADMIN"})
    public ResponseEntity<Void> banUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        userService.banUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT", "MANAGER"})
    public ResponseEntity<UserPresentDto> findUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER", "CLIENT"})
    public ResponseEntity<Void> updateUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestBody UserCreateDto dto){
        userService.updateUserById(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("updateReservationDays/{id}")
    @CheckPrivilege(roles = {"ADMIN"})
    public ResponseEntity<Void> updateReservationDays(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestParam int numOfDays){
        userService.updateUserReservationDays(id, numOfDays);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
