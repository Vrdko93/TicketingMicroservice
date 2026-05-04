package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synergisticit.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
