package com.rusoft.hrms.service;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rusoft.hrms.model.WorkLog;
import com.rusoft.hrms.repository.WorkLogRepository;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    public WorkLog post(WorkLog entity) {
        WorkLog workLog = workLogRepository.save(entity);
        return workLog;
    }

    public List<WorkLog> get(Integer employeeId,Date startDate,Date endDate) {
        List<WorkLog> workLogs = workLogRepository.findAllByEmployeeIdAndDateBetween(employeeId,startDate,endDate);
        return workLogs;
    }

}
