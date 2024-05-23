package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.models.RoleDTO;
import com.quanchun.backendexamsystem.repositories.RoleRepository;
import com.quanchun.backendexamsystem.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addNewRole(RoleDTO newRole) {
        Role role = Role.builder().name(newRole.getName()).build();
        return roleRepository.save(role);

    }

    @Override
    public Role getRoleById(int roleId) throws RoleNotFoundException {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(!optionalRole.isPresent())throw new RoleNotFoundException("Role not found");
        return optionalRole.get();
    }
}
