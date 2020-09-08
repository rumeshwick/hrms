package com.rusoft.hrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rusoft.hrms.model.Department;
import com.rusoft.hrms.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Department> getList() {
		return departmentRepository.findAll();
	}

}
