package com.synergisticit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.synergisticit.model.Employee;
import com.synergisticit.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

	    @Override
	    public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {

	        Employee employee =
	                employeeRepository.findByEmail(email)
	                .orElseThrow(() ->
	                    new UsernameNotFoundException("User not found"));
	        List<GrantedAuthority> authorities =
	                employee.getRoles()
	                        .stream()
	                        .map(role ->
	                                new SimpleGrantedAuthority(
	                                        "ROLE_" + role.getName()
	                                )
	                        )
	                        .collect(Collectors.toList());

	        return new User(
	                employee.getEmail(),
	                employee.getPassword(),
	                authorities
	        );
	    }
}
