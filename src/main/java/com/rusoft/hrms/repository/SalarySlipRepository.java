package com.rusoft.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

import com.rusoft.hrms.model.*;

@Repository
public interface SalarySlipRepository extends JpaRepository<SalarySlip, Integer> {

    SalarySlip findByEmployeeIdAndDate(Integer employeeId, Date sate);

}
