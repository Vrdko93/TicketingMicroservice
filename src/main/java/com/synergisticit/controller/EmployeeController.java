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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.model.Employee;
import com.synergisticit.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	public EmployeeController() {}
	
	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		
		return ResponseEntity.ok(employeeService.createEmployee(employee));
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
		
	    return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {

	    Employee updatedEmployee = employeeService.updateEmployee(id, employee);
	    
	    return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

	    employeeService.deleteEmployee(id);
	    
	    return ResponseEntity.ok("Employee deleted");
	}

	@PostMapping("/employees/{id}/roles")
	public ResponseEntity<String> assignRole(@PathVariable Long id, @RequestParam String roleName) {
		
		employeeService.assignRole(id, roleName);
		
	    return ResponseEntity.ok("Role assigned successfully");
	}
}
