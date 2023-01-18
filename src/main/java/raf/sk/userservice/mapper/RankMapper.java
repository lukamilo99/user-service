package raf.sk.userservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.rank.PresentRankDto;
import raf.sk.userservice.model.UserRank;

@Component
public class RankMapper {
    PresentRankDto toDto(UserRank rank){
        PresentRankDto dto = new PresentRankDto();
        dto.setDiscount(rank.getDiscount());
        dto.setType(rank.getType());

        return dto;
    }
}
