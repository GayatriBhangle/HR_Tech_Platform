package com.hrms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.entities.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeId(Long employeeId);
}
