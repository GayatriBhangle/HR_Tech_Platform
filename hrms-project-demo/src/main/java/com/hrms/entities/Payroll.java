package com.hrms.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Table(name="payroll")
@AttributeOverride(name = "id", column = @Column(name = "payroll_id"))
public class Payroll extends BaseEntity{
	
	@Column(name ="basic_salary", nullable = false, precision = 10, scale = 2)
	private BigDecimal basicSalary;

	@Column(precision = 10, scale = 2)
	private BigDecimal bonus;

	@Column(precision = 10, scale = 2)
	private BigDecimal deductions;

	@Column(name ="net_salary",precision = 10, scale = 2)
	private BigDecimal netSalary;
	
	@Column(name = "payroll_date", nullable = false)
	private LocalDate payrollDate;
//	
//	@Column(name = "payroll_month", nullable = false)
//	private Integer payrollMonth;
//
//	@Column(name = "payroll_year",nullable = false)
//	private Integer payrollYear;
	
	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

}
