package com.rusoft.hrms.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.rusoft.hrms.service.EmployeeService;
import com.rusoft.hrms.service.WorkLogService;
import com.rusoft.hrms.model.WorkLog;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("api/v1/work_log")
@RestController
public class WorkLogController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private WorkLogService workLogService;

	@PostMapping("/save")
	public Map<String, Object> save(@RequestBody WorkLog workLog) {

		Map<String, Object> response = new HashMap<>();

		if (!employeeService.get(workLog.getEmployee().getId()).isPresent()) {
			response.put("error", "Employee not found");
			return response;
		}

		workLog.setEmployee(employeeService.get(workLog.getEmployee().getId()).get());

		workLogService.post(workLog);
		response.put("Success", "Work log updated");
		return response;
	}

}
