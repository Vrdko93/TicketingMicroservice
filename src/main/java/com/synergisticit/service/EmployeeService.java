package com.synergisticit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.synergisticit.model.Employee;
import com.synergisticit.model.Role;
import com.synergisticit.model.Ticket;
import com.synergisticit.model.TicketHistory;
import com.synergisticit.repository.EmployeeRepository;
import com.synergisticit.repository.RoleRepository;
import com.synergisticit.repository.TicketHistoryRepository;
import com.synergisticit.repository.TicketRepository;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private TicketRepository ticketRepository; 
	
	@Autowired
	private TicketHistoryRepository ticketHistoryRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	
	public EmployeeService() {}
	
    public Employee createEmployee(Employee employee) {
    	
        Role role = roleRepository.findById(employee.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        employee.setRoles(new ArrayList<>(List.of(role)));
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
    	
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
    	
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees() {
    	
        return employeeRepository.findAll();
    }
    
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        
        if(updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isBlank()) {

        	existingEmployee.setPassword(passwordEncoder.encode( updatedEmployee.getPassword()));
        }
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setProject(updatedEmployee.getProject());
        existingEmployee.setManagerId(updatedEmployee.getManagerId());

        // update role
        if (updatedEmployee.getRoleId() != null) {

            Role role = roleRepository.findById(updatedEmployee.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            existingEmployee.setRoles(new ArrayList<>(List.of(role)));
        }

        return employeeRepository.save(existingEmployee);
    }
    
    public void deleteEmployee(Long id) {
    	
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        List<Ticket> createdTickets = ticketRepository.findByCreatedBy_Id(id);

        // remove reference to createdBy from the ticket side
        for (Ticket ticket : createdTickets) {
            ticket.setCreatedBy(null);
        }
        
        // remove reference to assignee from the ticket side
        List<Ticket> assignedTickets = ticketRepository.findByAssignee_Id(id);
        for (Ticket t : assignedTickets) {
            t.setAssignee(null);
        }
        
        // remove reference to actionBy from the TicketHistory side
        List<TicketHistory> histories = ticketHistoryRepository.findByActionById(id);

        for (TicketHistory h : histories) {
            h.setActionBy(null);
        }

        ticketRepository.saveAll(createdTickets);
        ticketRepository.saveAll(assignedTickets);
        ticketHistoryRepository.saveAll(histories);

        employeeRepository.delete(employee);
    }

    public void assignRole(Long employeeId, String roleName) {
    	
        Employee employee = getEmployeeById(employeeId);

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        employee.getRoles().add(role);
        employeeRepository.save(employee);
    }
}
