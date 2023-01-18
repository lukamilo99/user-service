package raf.sk.userservice.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.sk.userservice.dto.rank.PresentRankDto;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
public class UserPresentDto {
    private String name;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private Integer numberOfRentDays;
    private PresentRankDto rank;
}
