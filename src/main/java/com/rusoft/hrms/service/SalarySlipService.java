package com.rusoft.hrms.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rusoft.hrms.model.SalarySlip;
import com.rusoft.hrms.repository.SalarySlipRepository;

@Service
public class SalarySlipService {

    @Autowired
    private SalarySlipRepository salarySlipRepository;

    public SalarySlip post(SalarySlip entity) {
        SalarySlip salarySlip = salarySlipRepository.save(entity);
        return salarySlip;
    }

    public SalarySlip get(Integer employeeId, Date date) {
        SalarySlip salarySlip = salarySlipRepository.findByEmployeeIdAndDate(employeeId, date);
        return salarySlip;
    }

}
