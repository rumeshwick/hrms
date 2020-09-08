package com.rusoft.hrms.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.rusoft.hrms.service.DepartmentService;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("api/v1/department")
@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService deparmentService;

	@GetMapping
	public Map<String, Object> getList() {
		Map<String, Object> response = new HashMap<>();
		response.put("departments", deparmentService.getList());
		return response;
	}

}
