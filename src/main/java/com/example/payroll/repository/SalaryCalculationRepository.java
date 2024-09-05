package com.example.payroll.repository;

import com.example.payroll.model.SalaryCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaryCalculationRepository extends JpaRepository<SalaryCalculation, Long> {
    Optional<SalaryCalculation> findByNameAndMonth(String name, String month);

    List<SalaryCalculation> findAllByMonth(String month);
}
