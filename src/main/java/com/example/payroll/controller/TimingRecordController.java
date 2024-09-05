package com.example.payroll.controller;

import com.example.payroll.model.TimingRecord;
import com.example.payroll.service.TimingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
@RestController
@RequestMapping("/api/timing-records")
public class TimingRecordController {
    @Autowired
    private TimingRecordService timingRecordService;

    /**
     * 获取所有计时记录，支持按姓名过滤
     * @param name 可选的姓名过滤参数
     * @return 计时记录列表
     */
    @GetMapping
    public List<TimingRecord> getAllTimingRecords(@RequestParam(required = false) String name) {
        if (name != null) {
            return timingRecordService.getAllTimingRecords().stream()
                    .filter(record -> record.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
        }
        return timingRecordService.getAllTimingRecords();
    }

    /**
     * 更新指定ID的计时记录
     * * @param id 计时记录ID
     * @param timingRecord 更新后的计时记录对象
     * @return 更新后的计时记录
     */

    @PostMapping
    public TimingRecord createTimingRecord(@Valid @RequestBody TimingRecord timingRecord) {
        return timingRecordService.createTimingRecord(timingRecord);
    }

    /**
     * 更新指定ID的计时记录
     * @param id 计时记录ID
     * @param timingRecord 更新后的计时记录对象
     * @return 更新后的计时记录
     */
    @PutMapping("/{id}")
    public TimingRecord updateTimingRecord(@PathVariable Long id, @Valid @RequestBody TimingRecord timingRecord) {
        return timingRecordService.updateTimingRecord(id, timingRecord);
    }

    /**
     * 删除指定ID的计时记录
     * @param id 计时记录ID
     */
    @DeleteMapping("/{id}")
    public void deleteTimingRecord(@PathVariable Long id) {
        timingRecordService.deleteTimingRecord(id);
    }
}
