package com.hrms.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hrms.dtos.request.EmployeeRequestDTO;
import com.hrms.dtos.response.EmployeeResponseDTO;
import com.hrms.entities.Department;
import com.hrms.entities.Employee;
import com.hrms.exceptions.ResourceNotFoundException;
import com.hrms.repositories.DepartmentRepository;
import com.hrms.repositories.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
	
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final ModelMapper modelMapper;
	
	 /**
     * Helper method to convert Employee Entity to Response DTO
     */
    private EmployeeResponseDTO mapToResponseDto(Employee employee) {

        EmployeeResponseDTO dto = modelMapper.map(employee, EmployeeResponseDTO.class);

        dto.setDepartmentName(employee.getDepartment().getDepartmentName());

        if (employee.getManager() != null) {
            dto.setManagerName(
                    employee.getManager().getFirstName() + " "
                    + employee.getManager().getLastName());
        }

        return dto;
    }
    
    /**
     * Helper method to convert EmployeeRequestDTO to Employee Entity
     */
    private Employee mapToEntity(EmployeeRequestDTO dto) {
        return modelMapper.map(dto, Employee.class);
    }

//	@Override
//	public List<EmployeeResponseDTO> getAllEmployees() {
//		return employeeRepository.findAll()
//                .stream()
//                .map(this::mapToResponseDto)
//                .toList();
//	}
	
	@Override
	public EmployeeResponseDTO getEmployeeById(Long employeeId) {

	    Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Employee with id " + employeeId + " not found"));

	    return mapToResponseDto(employee);
	}

	@Override
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeRequestDto) {

	    // Fetch Department
	    Department department = departmentRepository.findById(employeeRequestDto.getDepartmentId())
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Department with id " + employeeRequestDto.getDepartmentId() + " not found"));

	    // Convert Request DTO to Entity
	    Employee employee = mapToEntity(employeeRequestDto);

	    // Set Department
	    employee.setDepartment(department);
	    
	    // Set password
	    employee.setHashedPwd(employeeRequestDto.getHashPwd());

	    // Set Manager (if provided)
	    if (employeeRequestDto.getManagerId() != null) {

	        Employee manager = employeeRepository.findById(employeeRequestDto.getManagerId())
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "Manager with id " + employeeRequestDto.getManagerId() + " not found"));

	        employee.setManager(manager);
	    }

	    // Save Employee
	    Employee savedEmployee = employeeRepository.save(employee);

	    // Convert Entity to Response DTO
	    return mapToResponseDto(savedEmployee);
	}

	//hard delete
	@Override
	public void hardDeleteEmployee(Long employeeId) {

	    if (!employeeRepository.existsById(employeeId)) {
	        throw new ResourceNotFoundException(
	                "Employee with id " + employeeId + " not found.");
	    }

	    employeeRepository.deleteById(employeeId);
	}
	
	//soft delete
	@Override
	public void softDeleteEmployee(Long employeeId) {

	    Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Employee with id " + employeeId + " not found."));

	    employee.setActive(false);

	    employeeRepository.save(employee);
	}
	
	@Override
	public List<EmployeeResponseDTO> getAllEmployees() {

	    return employeeRepository.findByIsActiveTrue()
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();
	}

	@Override
	public EmployeeResponseDTO updateEmployee(Long employeeId,
	        EmployeeRequestDTO employeeRequestDTO) {

	    // Fetch existing employee
	    Employee employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Employee with id " + employeeId + " not found"));

	    // Fetch department
	    Department department = departmentRepository.findById(employeeRequestDTO.getDepartmentId())
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Department with id " + employeeRequestDTO.getDepartmentId() + " not found"));

	    // Copy matching fields from DTO to existing entity
	    modelMapper.map(employeeRequestDTO, employee);

	    // Password field names are different
	    employee.setHashedPwd(employeeRequestDTO.getHashPwd());

	    // Set Department
	    employee.setDepartment(department);

	    // Set Manager
	    if (employeeRequestDTO.getManagerId() != null) {

	        Employee manager = employeeRepository.findById(employeeRequestDTO.getManagerId())
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "Manager with id " + employeeRequestDTO.getManagerId() + " not found"));

	        employee.setManager(manager);

	    } else {
	        employee.setManager(null);
	    }

	    Employee updatedEmployee = employeeRepository.save(employee);

	    return mapToResponseDto(updatedEmployee);
	}
}
