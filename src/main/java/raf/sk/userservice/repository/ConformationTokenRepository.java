package raf.sk.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.sk.userservice.model.ConformationToken;

import java.util.Optional;

public interface ConformationTokenRepository extends JpaRepository<ConformationToken, Long> {

    Optional<ConformationToken> findConformationTokenByToken(String token);
}
