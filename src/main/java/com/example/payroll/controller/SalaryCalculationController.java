package com.example.payroll.controller;

import com.example.payroll.model.SalaryCalculation;
import com.example.payroll.service.SalaryCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
@RestController
@RequestMapping("/api/salary-calculations")
public class SalaryCalculationController {
    @Autowired
    private SalaryCalculationService salaryCalculationService;

    @GetMapping
    public List<SalaryCalculation> getAllSalaryCalculations() {
        return salaryCalculationService.getAllSalaryCalculations();
    }

    @PostMapping
    public List<SalaryCalculation> calculateSalary(@RequestParam String month) {
        return salaryCalculationService.calculateSalary(month);
    }
}
