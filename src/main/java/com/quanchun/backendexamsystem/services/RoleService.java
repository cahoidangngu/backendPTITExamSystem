package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.models.RoleDTO;

public interface RoleService {
    Role addNewRole(RoleDTO newRole);

    Role getRoleById(Long roleId) throws RoleNotFoundException;

    boolean deleteRoleById(Long roleId) throws RoleNotFoundException;
}
