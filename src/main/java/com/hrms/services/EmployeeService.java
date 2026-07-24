package com.hrms.services;

import java.util.List;

import com.hrms.dtos.request.EmployeeRequestDTO;
import com.hrms.dtos.response.EmployeeResponseDTO;

public interface EmployeeService {

     EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeRequestDto);

    EmployeeResponseDTO updateEmployee(Long employeeId,
    		EmployeeRequestDTO employeeRequestDto);

    EmployeeResponseDTO getEmployeeById(Long employeeId);

    List<EmployeeResponseDTO> getAllEmployees();

    void hardDeleteEmployee(Long employeeId);
    
    void softDeleteEmployee(Long employeeId);
}
