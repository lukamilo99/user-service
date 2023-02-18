package raf.sk.userservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import raf.sk.userservice.dto.rank.PresentRankDto;
import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserCreateDto;
import raf.sk.userservice.dto.user.UserPresentDto;
import raf.sk.userservice.exception.RankNotFoundException;
import raf.sk.userservice.exception.RoleNotFoundException;
import raf.sk.userservice.exception.UserNotFoundException;
import raf.sk.userservice.mapper.UserMapper;
import raf.sk.userservice.model.Role;
import raf.sk.userservice.model.UserEntity;
import raf.sk.userservice.model.UserRank;
import raf.sk.userservice.repository.RankRepository;
import raf.sk.userservice.repository.RoleRepository;
import raf.sk.userservice.repository.UserRepository;
import raf.sk.userservice.security.JwtGenerator;
import raf.sk.userservice.service.UserService;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private RankRepository rankRepository;
    private RoleRepository roleRepository;
    private JwtGenerator jwtGenerator;
    private AuthenticationManager authenticationManager;

    @Override
    public void registerClient(UserCreateDto dto) {
        UserEntity userEntity = userMapper.userCreateDtoToUser(dto);
        UserRank rank = rankRepository.findById(1L).orElseThrow(() -> new RankNotFoundException("Rank not found"));
        Role role = roleRepository.findByType("CLIENT").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        userEntity.setRole(role);
        userEntity.setUserRank(rank);
        userEntity.setRoleTypeBeforeBan(role.getType());

        userRepository.save(userEntity);
    }

    @Override
    public void registerManager(UserCreateDto dto) {
        UserEntity userEntity = userMapper.userCreateDtoToUser(dto);
        Role role = roleRepository.findByType("MANAGER").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        userEntity.setRole(role);
        userEntity.setRoleTypeBeforeBan(role.getType());

        userRepository.save(userEntity);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        String username = tokenRequestDto.getUsername();
        String password = tokenRequestDto.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenResponseDto(jwtGenerator.generate(authentication));
    }

    @Override
    public UserPresentDto findUserById(Long id) {
        UserPresentDto userDto = new UserPresentDto();
        PresentRankDto rankDto = new PresentRankDto();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        rankDto.setType(userEntity.getUserRank().getType());
        rankDto.setDiscount(userEntity.getUserRank().getDiscount());
        userDto.setRank(rankDto);

        return userDto;

    }
    @Override
    public void banUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findByType("BANNED").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        userEntity.setRole(role);
    }

    @Override
    public void unbanUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findByType(userEntity.getRoleTypeBeforeBan()).orElseThrow(() -> new RoleNotFoundException("Role not found"));

        userEntity.setRole(role);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserById(Long id, UserCreateDto dto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.save(userMapper.userCreateDtoToUser(dto));
    }

    @Override
    public void updateUserReservationDays(Long id, int days) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserRank rankAfterUpdatedNumberOfDays;
        int updatedNumberOfDays;

        updatedNumberOfDays = userEntity.getNumberOfRentDays() + days;
        rankAfterUpdatedNumberOfDays = isEligibleForUpgrade(updatedNumberOfDays);

        if(!rankAfterUpdatedNumberOfDays.equals(userEntity.getUserRank())){
            userEntity.setUserRank(rankAfterUpdatedNumberOfDays);
        }

        userEntity.setNumberOfRentDays(updatedNumberOfDays);
    }

    private UserRank isEligibleForUpgrade(int numberOfReservationDays){
        return rankRepository.findUserRankByNumOfRentDay(numberOfReservationDays).orElseThrow(() -> new RankNotFoundException("User not found"));
    }
}
