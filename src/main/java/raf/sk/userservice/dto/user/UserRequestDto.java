package raf.sk.userservice.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
}
