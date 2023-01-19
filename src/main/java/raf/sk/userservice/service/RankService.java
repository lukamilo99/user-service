package raf.sk.userservice.service;

import raf.sk.userservice.model.UserRank;

public interface RankService {
    UserRank findRankByNumOfRentDays(int days);
}
