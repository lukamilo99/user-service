package raf.sk.userservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.userservice.dto.rank.RankResponseDto;
import raf.sk.userservice.model.UserRank;

@Component
public class RankMapper {

    RankResponseDto toDto(UserRank rank){
        RankResponseDto dto = new RankResponseDto();
        dto.setDiscount(rank.getDiscount());
        dto.setType(rank.getType());

        return dto;
    }
}
