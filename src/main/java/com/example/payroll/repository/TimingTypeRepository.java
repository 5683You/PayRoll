package com.example.payroll.repository;

import com.example.payroll.model.TimingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimingTypeRepository extends JpaRepository<TimingType, Long> {
}
