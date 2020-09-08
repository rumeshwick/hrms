package com.rusoft.hrms.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.rusoft.hrms.service.DepartmentService;
import com.rusoft.hrms.model.Department;

import java.util.List;

@RequestMapping("api/v1/department")
@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService deparmentService;

	@GetMapping
	public List<Department> getList() {
		return deparmentService.getList();
	}

}
