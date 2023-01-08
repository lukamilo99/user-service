package raf.sk.userservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.userservice.repository.RankRepository;
import raf.sk.userservice.service.RankService;

@Service
@Transactional
@AllArgsConstructor
public class RankServiceImplementation implements RankService {
    private RankRepository rankRepository;
    @Override
    public Integer findDiscountByNumOfRentDays(int days) {
        return rankRepository.findDiscountByNumOfRentDay(days).orElse(0);
    }
}
