package raf.sk.userservice.service;

import raf.sk.userservice.model.Role;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findRoleByType(String name);
}
