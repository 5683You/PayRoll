package com.example.payroll.service;

import com.example.payroll.model.TimingType;
import com.example.payroll.repository.TimingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimingTypeService {
    @Autowired
    private TimingTypeRepository timingTypeRepository;

    public List<TimingType> getAllTimingTypes() {
        return timingTypeRepository.findAll();
    }

    public TimingType createTimingType(TimingType timingType) {
        return timingTypeRepository.save(timingType);
    }

    public TimingType updateTimingType(Long id, TimingType timingType) {
        TimingType existingTimingType = timingTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimingType not found"));
        existingTimingType.setType(timingType.getType());
        existingTimingType.setRate(timingType.getRate());
        existingTimingType.setEnabled(timingType.getEnabled());
        return timingTypeRepository.save(existingTimingType);
    }
}
