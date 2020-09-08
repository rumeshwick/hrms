package com.rusoft.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.rusoft.hrms.model.*;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Override
    List<Department> findAll();

}
