package raf.sk.userservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.userservice.model.ConformationToken;
import raf.sk.userservice.model.UserEntity;
import raf.sk.userservice.repository.ConformationTokenRepository;
import raf.sk.userservice.service.ConformationTokenService;

@AllArgsConstructor
@Service
@Transactional
public class ConformationTokenServiceImpl implements ConformationTokenService {

    private ConformationTokenRepository tokenRepository;

    @Override
    public void saveConformationToken(ConformationToken token) {
        tokenRepository.save(token);
    }

    @Override
    public ConformationToken findConformationTokenByToken(String token) {
        return tokenRepository.findConformationTokenByToken(token).orElseThrow(() -> new RuntimeException("No token"));
    }

    @Override
    public ConformationToken createToken(UserEntity user, String type){
        return new ConformationToken(user, type);
    }
}
