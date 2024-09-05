package com.example.payroll.controller;

import com.example.payroll.model.TimingType;
import com.example.payroll.service.TimingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
@RestController
@RequestMapping("/api/timing-types")
public class TimingTypeController {
    @Autowired
    private TimingTypeService timingTypeService;

    /**
     * 获取所有计时类型
     * @return 计时类型列表
     */
    @GetMapping
    public List<TimingType> getAllTimingTypes() {
        return timingTypeService.getAllTimingTypes();
    }

    /**
     * 创建新的计时类型
     * @param timingType 计时类型对象
     * @return 创建的计时类型
     */
    @PostMapping
    public TimingType createTimingType(@Valid @RequestBody TimingType timingType) {
        return timingTypeService.createTimingType(timingType);
    }

    /**
     * 更新指定ID的计时类型
     * @param id 计时类型ID
     * @param timingType 更新后的计时类型对象
     * @return 更新后的计时类型
     */
    @PutMapping("/{id}")
    public TimingType updateTimingType(@PathVariable Long id, @Valid @RequestBody TimingType timingType) {
        return timingTypeService.updateTimingType(id, timingType);
    }
}
