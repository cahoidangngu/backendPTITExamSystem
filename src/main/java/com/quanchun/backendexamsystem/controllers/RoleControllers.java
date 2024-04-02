package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.RoleDTO;
import com.quanchun.backendexamsystem.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class RoleControllers {
    @Autowired
    private RoleService roleService;


    @PostMapping("/addNewRole")
    public ResponseEntity<String> addNewRole(@RequestBody @Valid RoleDTO newRole){
        Role role = roleService.addNewRole(newRole);
        String responseBody = String.format("Cannot add %s Role", newRole.getName());
        if(role!=null){
            responseBody = String.format("Successfully added %s Role", role.getName());
        }
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable("id") Long roleId) throws RoleNotFoundException {
        String responseBody = "Cannot delete Role";
        if(roleService.deleteRoleById(roleId)){
            responseBody = "Successfully delete Role";
        }
        return ResponseEntity.ok(responseBody);
    }
}
