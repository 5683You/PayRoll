package com.example.payroll.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class SalaryCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double totalHours;
    private Double totalSalary;
    private String month;

    @ManyToOne
    @JoinColumn(name = "timing_type_id")
    private TimingType timingType;

    // Getters and Setters
}
