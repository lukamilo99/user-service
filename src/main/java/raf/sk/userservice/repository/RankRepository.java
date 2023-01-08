package raf.sk.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import raf.sk.userservice.model.UserRank;

import java.util.Optional;

@Repository
public interface RankRepository extends JpaRepository<UserRank, Long> {
    @Query("SELECT discount FROM UserRank WHERE ?1 BETWEEN minNumberOfRentDays AND maxNumberOfRentDays")
    Optional<Integer> findDiscountByNumOfRentDay(int numberOfRentDays);
}
