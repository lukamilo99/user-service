package raf.sk.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private Integer numberOfRentDays;
    @ManyToOne(optional = false)
    private Role role;
    @ManyToOne
    private UserRank userRank;
    private String roleTypeBeforeBan;
    private boolean enabled;
}
