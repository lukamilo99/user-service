package raf.sk.userservice.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.userservice.dto.rank.PresentRankDto;
import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserCreateDto;
import raf.sk.userservice.dto.user.UserPresentDto;
import raf.sk.userservice.mapper.UserMapper;
import raf.sk.userservice.model.Role;
import raf.sk.userservice.model.User;
import raf.sk.userservice.model.UserRank;
import raf.sk.userservice.repository.RankRepository;
import raf.sk.userservice.repository.RoleRepository;
import raf.sk.userservice.repository.UserRepository;
import raf.sk.userservice.security.service.TokenService;
import raf.sk.userservice.service.UserService;

import java.util.Optional;
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private RankRepository rankRepository;
    private RoleRepository roleRepository;
    private TokenService tokenService;

    @Override
    public void registerClient(UserCreateDto dto) {
        User user = userMapper.userCreateDtoToUser(dto);
        Optional<UserRank> rank = rankRepository.findById(1L);
        Optional<Role> role = roleRepository.findByType("CLIENT");
        rank.ifPresent(user::setUserRank);
        role.ifPresent(user::setRole);
        role.ifPresent(role1 -> user.setRoleTypeBeforeBan(role1.getType()));

        userRepository.save(user);
    }

    @Override
    public void registerManager(UserCreateDto dto) {
        User user = userMapper.userCreateDtoToUser(dto);
        Optional<Role> role = roleRepository.findByType("MANAGER");
        role.ifPresent(user::setRole);
        role.ifPresent(role1 -> user.setRoleTypeBeforeBan(role1.getType()));

        userRepository.save(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        String username = tokenRequestDto.getUsername();
        String password = tokenRequestDto.getPassword();
        User user = userRepository.findByUsernameAndPassword(username, password).orElse(null); //bice exception ovde

        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getType());
        return new TokenResponseDto(tokenService.generateToken(claims));
    }

    @Override
    public UserPresentDto findUserById(Long id) {
        UserPresentDto dto = new UserPresentDto();
        PresentRankDto rankDto = new PresentRankDto();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            rankDto.setType(user.get().getUserRank().getType());
            rankDto.setDiscount(user.get().getUserRank().getDiscount());
            dto.setRank(rankDto);
            return dto;
        }
        else{
            System.out.println("There is no user with id " + id);
            return new UserPresentDto();
        }
    }
    @Override
    public void banUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().setRole(roleRepository.findByType("BANNED").get());
            userRepository.save(user.get());
        }
    }

    @Override
    public void unbanUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().setRole(roleRepository.findByType(user.get().getRoleTypeBeforeBan()).get());
            userRepository.save(user.get());
        }
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserById(Long id, UserCreateDto dto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.save(userMapper.userCreateDtoToUser(dto));
        }
        else{
            System.out.println("There is no user with id " + id);
        }
    }

    @Override
    public void updateUserReservationDays(Long id, int days) {
        Optional<User> user = userRepository.findById(id);
        int updatedNumberOfDays;
        UserRank rankAfterUpdatedNumberOfDays;

        if(user.isPresent()){
            updatedNumberOfDays = user.get().getNumberOfRentDays() + days;
            rankAfterUpdatedNumberOfDays = isEligibleForUpgrade(updatedNumberOfDays);

            if(!rankAfterUpdatedNumberOfDays.equals(user.get().getUserRank())){
                user.get().setUserRank(rankAfterUpdatedNumberOfDays);
            }

            user.get().setNumberOfRentDays(updatedNumberOfDays);
        }
        else{
            System.out.println("There is no user with id " + id);
        }
    }

    private UserRank isEligibleForUpgrade(int numberOfReservationDays){
        Optional<UserRank> rank = rankRepository.findUserRankByNumOfRentDay(numberOfReservationDays);

        if(rank.isPresent()){
            return rank.get();
        }
        return null;
    }
}
