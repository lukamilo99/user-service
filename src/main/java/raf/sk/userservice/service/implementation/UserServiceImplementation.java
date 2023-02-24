package raf.sk.userservice.service.implementation;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import raf.sk.userservice.dto.auth.UserDetailsDto;
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

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private RankRepository rankRepository;
    private RoleRepository roleRepository;
    private JwtUtils jwtUtils;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetails userDetails;

    public UserServiceImplementation(UserRepository userRepository, UserMapper userMapper, RankRepository rankRepository, RoleRepository roleRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rankRepository = rankRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerClient(UserRequestDto dto) {
        UserEntity userEntity = userMapper.userRequestDtoToUser(dto);
        UserRank rank = rankRepository.findById(1L).orElseThrow(() -> new RankNotFoundException("Rank not found"));
        Role role = roleRepository.findByType("CLIENT").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        userEntity.setRole(role);
        userEntity.setUserRank(rank);
        userEntity.setRoleTypeBeforeBan(role.getType());

        userRepository.save(userEntity);
    }

    @Override
    public void registerManager(UserRequestDto dto) {
        UserEntity userEntity = userMapper.userRequestDtoToUser(dto);
        Role role = roleRepository.findByType("MANAGER").orElseThrow(() -> new RoleNotFoundException("Role not found"));

        userEntity.setRole(role);
        userEntity.setRoleTypeBeforeBan(role.getType());

        userRepository.save(userEntity);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        String username = tokenRequestDto.getUsername();
        String password = tokenRequestDto.getPassword();

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        UserDetailsDto userDetailsDto = new UserDetailsDto(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getRole().getType());

        if(passwordEncoder.matches(password, user.getPassword())){
            return new TokenResponseDto(jwtUtils.generateToken(userDetailsDto));
        }
        else throw new UserNotFoundException("Incorrect password");
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
        userDetails = getUserDetails();
        Long id = userDetails.getId();

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userEntity.setName(dto.getName());
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
        return rankRepository.findUserRankByNumOfRentDay(numberOfReservationDays).orElseThrow(() -> new RankNotFoundException("Rank not found"));
    }

    private CustomUserDetails getUserDetails(){
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
