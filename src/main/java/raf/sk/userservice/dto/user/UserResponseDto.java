package raf.sk.userservice.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.sk.userservice.dto.rank.RankResponseDto;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private RankResponseDto rank;
}
