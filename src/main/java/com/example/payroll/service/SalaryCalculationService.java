package com.example.payroll.service;

import com.example.payroll.model.SalaryCalculation;
import com.example.payroll.model.TimingRecord;
import com.example.payroll.repository.SalaryCalculationRepository;
import com.example.payroll.repository.TimingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryCalculationService {
    @Autowired
    private SalaryCalculationRepository salaryCalculationRepository;

    @Autowired
    private TimingRecordRepository timingRecordRepository;

    public List<SalaryCalculation> getAllSalaryCalculations() {
        return salaryCalculationRepository.findAll();
    }

    public List<SalaryCalculation> calculateSalary(String month) {
        List<TimingRecord> timingRecords = timingRecordRepository.findAll();

        // 按姓名分组并计算总工时和总工资
        Map<String, List<TimingRecord>> groupedRecords = timingRecords.stream()
                .filter(record -> record.getCreatedAt().toString().startsWith(month))
                .collect(Collectors.groupingBy(TimingRecord::getName));

        groupedRecords.forEach((name, records) -> {
            double totalHours = records.stream().mapToDouble(TimingRecord::getHours).sum();
            double totalSalary = records.stream().mapToDouble(record -> record.getHours() * record.getTimingType().getRate()).sum();

            // 检查是否已经存在该月份的记录
            Optional<SalaryCalculation> existingRecord = salaryCalculationRepository.findByNameAndMonth(name, month);
            SalaryCalculation salaryCalculation;
            if (existingRecord.isPresent()) {
                salaryCalculation = existingRecord.get();
                salaryCalculation.setTotalHours(totalHours);
                salaryCalculation.setTotalSalary(totalSalary);
                salaryCalculation.setTimingType(records.get(0).getTimingType()); // 设置 timingType
            } else {
                salaryCalculation = new SalaryCalculation();
                salaryCalculation.setName(name);
                salaryCalculation.setTotalHours(totalHours);
                salaryCalculation.setTotalSalary(totalSalary);
                salaryCalculation.setMonth(month);
                salaryCalculation.setTimingType(records.get(0).getTimingType()); // 设置 timingType
            }

            salaryCalculationRepository.save(salaryCalculation);
        });

        // 返回包含详细信息的工资核算记录
        return timingRecords.stream()
                .filter(record -> record.getCreatedAt().toString().startsWith(month))
                .map(record -> {
                    SalaryCalculation salaryCalculation = new SalaryCalculation();
                    salaryCalculation.setName(record.getName());
                    salaryCalculation.setTotalHours(record.getHours());
                    salaryCalculation.setTotalSalary(record.getHours() * record.getTimingType().getRate());
                    salaryCalculation.setMonth(month);
                    salaryCalculation.setTimingType(record.getTimingType()); // 添加 timingType 信息
                    return salaryCalculation;
                })
                .collect(Collectors.toList());
    }
}
