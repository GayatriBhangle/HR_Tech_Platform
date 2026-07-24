package com.hrms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entities.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmail(String email);
	
	Optional<Employee> findById(Long employeeId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNo(String phoneNo);
    
    //for soft delete
    List<Employee> findByIsActiveTrue();
}
