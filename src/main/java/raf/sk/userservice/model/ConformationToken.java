package raf.sk.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConformationToken {

    private final long EXPIRATION_TIME = 120000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private UserEntity user;
    private String type;
    private String token;
    private Date expireDate;

    public ConformationToken(UserEntity user, String type) {

        this.user = user;
        this.type = type;
        this.token = UUID.randomUUID().toString();
        this.expireDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }
}
