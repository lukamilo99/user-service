package raf.sk.userservice.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import raf.sk.userservice.exception.UserNotFoundException;
import raf.sk.userservice.model.Role;
import raf.sk.userservice.model.UserEntity;
import raf.sk.userservice.repository.UserRepository;
import raf.sk.userservice.security.CustomUserDetails;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User " + username + " not found"));

        return new CustomUserDetails(userEntity.getUsername(), userEntity.getPassword(), Collections.singleton(roleToAuthority(userEntity.getRole())), userEntity.getId());
    }

    private GrantedAuthority roleToAuthority(Role role){
        return new SimpleGrantedAuthority(role.getType());
    }
}
