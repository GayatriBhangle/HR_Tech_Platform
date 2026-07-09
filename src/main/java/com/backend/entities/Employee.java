package com.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "f_name", nullable = false, length = 100)
    private String fName;

    @Column(name = "l_name", nullable = false, length = 100)
    private String lName;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone_no", length = 15)
    private String phoneNo;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    // Self-referencing: manager_id FK (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "emp_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Employee> subordinates;

    // FK: dept_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    // Relationships
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Payroll payroll;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Leave> leaves;

    @OneToMany(mappedBy = "approvedBy", fetch = FetchType.LAZY)
    private List<Leave> approvedLeaves;
}