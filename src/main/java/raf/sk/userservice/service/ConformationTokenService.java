package raf.sk.userservice.service;

import raf.sk.userservice.model.ConformationToken;
import raf.sk.userservice.model.UserEntity;


public interface ConformationTokenService {

    void saveConformationToken(ConformationToken token);
    ConformationToken findConformationTokenByToken(String token);
    ConformationToken createToken(UserEntity user, String type);

}
