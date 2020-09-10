package com.rusoft.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import com.rusoft.hrms.model.*;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Integer> {

    @Override
    List<WorkLog> findAll();

    List<WorkLog> findAllByEmployeeIdAndDateBetween(Integer employeeId,Date startDate,Date endDate);

}
