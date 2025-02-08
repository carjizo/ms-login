package com.login.services.impl;

import com.login.dtos.RoleAllDTO;
import com.login.entities.Role;
import com.login.repositories.RoleRepository;
import com.login.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<RoleAllDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(role -> RoleAllDTO.builder()
                        .id(role.getId())
                        .roleName(role.getRoleName())
                        .build())
                .toList();
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
