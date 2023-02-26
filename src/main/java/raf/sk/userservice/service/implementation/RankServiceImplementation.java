package raf.sk.userservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.userservice.model.UserRank;
import raf.sk.userservice.repository.RankRepository;
import raf.sk.userservice.service.RankService;

@AllArgsConstructor
@Service
@Transactional
public class RankServiceImplementation implements RankService {
    private RankRepository rankRepository;
    @Override
    public UserRank findRankByNumOfRentDays(int days) {
        return rankRepository.findUserRankByNumOfRentDay(days).orElse(null);
    }
}
