package com.backend.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name = "payroll",
    uniqueConstraints = {
        // One payroll record per employee per month (pay_month stored as first day of month)
        @UniqueConstraint(columnNames = {"emp_id", "pay_month"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long payId;

    @Column(name = "pay_month", nullable = false)
    private LocalDate payMonth;

    @Column(name = "basic_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal basicSalary;

    @Column(name = "bonus", precision = 12, scale = 2)
    private BigDecimal bonus;

    @Column(name = "deductions", precision = 12, scale = 2)
    private BigDecimal deductions;

    /**
     * Derived / generated column.
     * net_salary = basic_salary + bonus - deductions
     * @Formula avoids storing a redundant computed value; Hibernate reads it via SQL.
     */
    @Formula("(basic_salary + COALESCE(bonus, 0) - COALESCE(deductions, 0))")
    private BigDecimal netSalary;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    // 1:1 with Employee
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false, unique = true)
    private Employee employee;
}