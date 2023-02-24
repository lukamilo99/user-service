package raf.sk.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.userservice.dto.user.UserResponseDto;
import raf.sk.userservice.service.UserService;

@AllArgsConstructor
@RequestMapping("/inter-service")
@RestController
public class InterServiceController {

    private UserService userService;

    @PutMapping("/updateReservationDays/{id}")
    public ResponseEntity<Void> updateReservationDays(@PathVariable Long id, @RequestParam(value = "numOfDays") int numOfDays){
        userService.updateUserReservationDays(id, numOfDays);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }
}
