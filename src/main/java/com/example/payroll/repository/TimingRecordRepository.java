package com.example.payroll.repository;

import com.example.payroll.model.TimingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimingRecordRepository extends JpaRepository<TimingRecord, Long> {
}
