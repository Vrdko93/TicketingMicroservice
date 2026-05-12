package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.model.Role;
import com.synergisticit.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	public RoleService() {}
	
	public Role createRole(Role role) {
		
	    return roleRepository.save(role);
	}

	public Role getRoleById(Long id) {
		
		return roleRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Role not found"));
	}

	public Role getRoleByName(String name) {
		
	    return roleRepository.findByName(name)
	                .orElseThrow(() -> new RuntimeException("Role not found"));
	}

	public List<Role> getAllRoles() {
		
		return roleRepository.findAll();
	}
	
    public Role updateRole(Long id, Role updatedRole) {

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existingRole.setName(updatedRole.getName());

        return roleRepository.save(existingRole);
    }

	public void deleteRole(Long id) {
		
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roleRepository.delete(role);
	}
}
