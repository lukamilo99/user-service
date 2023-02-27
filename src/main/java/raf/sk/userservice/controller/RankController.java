package raf.sk.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.sk.userservice.model.UserRank;
import raf.sk.userservice.service.RankService;

@RestController
@RequestMapping("/rank")
@AllArgsConstructor
public class RankController {

    private RankService rankService;

    @GetMapping("/discount/{days}")
    public ResponseEntity<UserRank> findDiscountByNumOfRentDays(@PathVariable int days){
        return new ResponseEntity<>(rankService.findRankByNumOfRentDays(days), HttpStatus.OK);
    }
}
