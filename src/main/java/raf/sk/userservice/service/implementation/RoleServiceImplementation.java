package raf.sk.userservice.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.userservice.model.Role;
import raf.sk.userservice.repository.RoleRepository;
import raf.sk.userservice.service.RoleService;

import java.util.Optional;
@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImplementation implements RoleService {
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findRoleByType(String type) {
        return roleRepository.findByType(type);
    }
}
