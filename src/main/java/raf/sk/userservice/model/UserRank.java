package raf.sk.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Integer minNumberOfRentDays;
    private Integer maxNumberOfRentDays;
    private Integer discount;
    @OneToMany(mappedBy = "userRank")
    private List<User> listOfUsers;
}
