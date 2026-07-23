package com.hrms.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.dtos.request.EmployeeRequestDTO;
import com.hrms.dtos.response.EmployeeResponseDTO;
import com.hrms.services.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	/**
     * Get All Employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {

        List<EmployeeResponseDTO> employees =
                employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable Long employeeId) {

        EmployeeResponseDTO employee = employeeService.getEmployeeById(employeeId);

        return ResponseEntity.ok(employee);
    }
    
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> addEmployee(
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        EmployeeResponseDTO employee =
                employeeService.addEmployee(employeeRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employee);
    }
    
    //hard delete
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {

        //employeeService.hardDeleteEmployee(employeeId);
        employeeService.softDeleteEmployee(employeeId);

        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        EmployeeResponseDTO updatedEmployee =
                employeeService.updateEmployee(employeeId, employeeRequestDTO);

        return ResponseEntity.ok(updatedEmployee);
    }
}
