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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity user;
    private String type;
    private String token;
    private Date expireDate;

    public ConformationToken(UserEntity user, String type) {

        this.user = user;
        this.type = type;
        this.token = UUID.randomUUID().toString();
        this.expireDate = new Date(System.currentTimeMillis() + 120000L);
    }
}
