package com.hrms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.entities.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);
}
