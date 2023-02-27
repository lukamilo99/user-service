package raf.sk.userservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raf.sk.userservice.dto.rank.RankResponseDto;
import raf.sk.userservice.dto.token.TokenRequestDto;
import raf.sk.userservice.dto.token.TokenResponseDto;
import raf.sk.userservice.dto.user.UserRequestDto;
import raf.sk.userservice.dto.user.UserResponseDto;
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
import raf.sk.userservice.security.jwt.JwtUtils;
import raf.sk.userservice.security.model.CustomUserDetails;
import raf.sk.userservice.service.UserService;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private RankRepository rankRepository;
    private RoleRepository roleRepository;
    private JwtUtils jwtUtils;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Override
    public UserEntity registerClient(UserRequestDto dto) {
        UserEntity user = userMapper.userRequestDtoToUser(dto);
        UserRank rank = rankRepository.findById(1L).orElseThrow(() -> new RankNotFoundException("Rank not found"));
        Role role = roleRepository.findByType("CLIENT").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        user.setRole(role);
        user.setUserRank(rank);
        user.setRoleTypeBeforeBan(role.getType());

        userRepository.save(user);
        return user;
    }

    @Override
    public UserEntity registerManager(UserRequestDto dto) {
        UserEntity user = userMapper.userRequestDtoToUser(dto);
        Role role = roleRepository.findByType("MANAGER").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        user.setRole(role);
        user.setRoleTypeBeforeBan(role.getType());

        userRepository.save(user);
        return user;
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        String username = tokenRequestDto.getUsername();
        String password = tokenRequestDto.getPassword();

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        if(passwordEncoder.matches(password, user.getPassword()) && user.isEnabled()){
            return new TokenResponseDto(jwtUtils.generateToken(user.getId(), user.getRole().getType()));
        }
        else throw new UserNotFoundException("Incorrect password or user not confirmed");
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        UserResponseDto userDto = new UserResponseDto();
        RankResponseDto rankDto = new RankResponseDto();
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
    public void updateUserById(UserRequestDto dto) {
        CustomUserDetails userDetails = getUserDetails();
        Long id = userDetails.getId();

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userEntity.setName(dto.getFirstname());
    }

    @Override
    public void updateUserReservationDays(Long id, int days) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        int updatedNumberOfDays = userEntity.getNumberOfRentDays() + days;
        UserRank rankAfterUpdatedNumberOfDays = isEligibleForUpgrade(updatedNumberOfDays);

        if(!rankAfterUpdatedNumberOfDays.equals(userEntity.getUserRank())){
            userEntity.setUserRank(rankAfterUpdatedNumberOfDays);
        }

        userEntity.setNumberOfRentDays(updatedNumberOfDays);
    }

    private UserRank isEligibleForUpgrade(int numberOfReservationDays){
        return rankRepository.findUserRankByNumOfRentDay(numberOfReservationDays).orElseThrow(() -> new RankNotFoundException("Rank not found"));
    }

    private CustomUserDetails getUserDetails(){
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
