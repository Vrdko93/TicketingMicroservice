package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.model.Employee;
import com.synergisticit.model.Role;
import com.synergisticit.repository.EmployeeRepository;
import com.synergisticit.repository.RoleRepository;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	public EmployeeService() {}
	
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void assignRole(Long employeeId, String roleName) {
        Employee employee = getEmployeeById(employeeId);

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        employee.getRoles().add(role);
        employeeRepository.save(employee);
    }
}
