package raf.sk.userservice.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
