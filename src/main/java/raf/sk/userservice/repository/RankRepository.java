package raf.sk.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import raf.sk.userservice.model.UserRank;

import java.util.Optional;

@Repository
public interface RankRepository extends JpaRepository<UserRank, Long> {
    @Query("SELECT r FROM UserRank r WHERE ?1 BETWEEN r.minNumberOfRentDays AND r.maxNumberOfRentDays")
    Optional<UserRank> findUserRankByNumOfRentDay(int numberOfRentDays);
}
