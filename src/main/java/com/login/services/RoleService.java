package com.login.services;

import com.login.dtos.RoleAllDTO;
import com.login.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleAllDTO> findAll();
    Optional<Role> findByRoleName(String roleName);
}
