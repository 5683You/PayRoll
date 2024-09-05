package com.example.payroll.service;

import com.example.payroll.model.TimingRecord;
import com.example.payroll.repository.TimingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimingRecordService {
    @Autowired
    private TimingRecordRepository timingRecordRepository;

    public List<TimingRecord> getAllTimingRecords() {
        return timingRecordRepository.findAll();
    }

    public TimingRecord createTimingRecord(TimingRecord timingRecord) {
        return timingRecordRepository.save(timingRecord);
    }

    public TimingRecord updateTimingRecord(Long id, TimingRecord timingRecord) {
        TimingRecord existingTimingRecord = timingRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimingRecord not found"));
        existingTimingRecord.setName(timingRecord.getName());
        existingTimingRecord.setHours(timingRecord.getHours());
        existingTimingRecord.setTimingType(timingRecord.getTimingType());
        return timingRecordRepository.save(existingTimingRecord);
    }

    public void deleteTimingRecord(Long id) {
        timingRecordRepository.deleteById(id);
    }
}
