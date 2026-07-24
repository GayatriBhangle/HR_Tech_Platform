package com.hrms.dtos.response;

import java.time.LocalDate;

import com.hrms.enums.Gender;
import com.hrms.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private String phoneNo;

    private Role role;

    private LocalDate joinDate;

    private Integer performanceRating;

    private String departmentName;

    private String managerName;
}
