package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.model.Role;
import com.synergisticit.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	public RoleController() {}
	
	@PostMapping("/roles")
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		
		return ResponseEntity.ok(roleService.createRole(role));
	}

	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRoles() {
		
	    return ResponseEntity.ok(roleService.getAllRoles());
	}

	@GetMapping("/roles/{id}")
	public ResponseEntity<Role> getRole(@PathVariable Long id) {
		
		return ResponseEntity.ok(roleService.getRoleById(id));
	}
	
	@PutMapping("/roles/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {

	    Role updatedRole = roleService.updateRole(id, role);

	    return ResponseEntity.ok(updatedRole);
	}

	@DeleteMapping("/roles/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable Long id) {
		
		roleService.deleteRole(id);
		
	    return ResponseEntity.ok("Role deleted");
	}
}
