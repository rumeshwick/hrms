package com.rusoft.hrms.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.rusoft.hrms.service.EmployeeService;
import com.rusoft.hrms.service.WorkLogService;
import com.rusoft.hrms.service.SalarySlipService;
import com.rusoft.hrms.model.Employee;
import com.rusoft.hrms.model.SalarySlip;
import com.rusoft.hrms.model.WorkLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/salary_slip")
@RestController
public class SalarySlipController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private WorkLogService workLogService;

	@Autowired
	private SalarySlipService salarySlipService;

	// TO DO This needs to be taken from a table
	private final static Integer HOURLY_RATE = 1500;

	SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyy-MM");

	@PostMapping("/generate")
	public Map<String, Object> save(@RequestBody Map<String, Object> requestInfo) throws ParseException {

		System.out.println(requestInfo.get("employeeId"));

		Map<String, Object> response = new HashMap<>();

		Integer employeeId = (Integer) requestInfo.get("employeeId");

		if (!employeeService.get(employeeId).isPresent()) {
			response.put("error", "Employee not found");
			return response;
		}

		Employee employee = employeeService.get(employeeId).get();

		String requestMonth = ((String) requestInfo.get("month")) + "-01";

		LocalDate startDate = LocalDate.parse(requestMonth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
		Date startDateUTC = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDateUTC = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<WorkLog> worklogs = workLogService.get(employeeId, startDateUTC, endDateUTC);

		Integer totalWorkHours = 0;
		for (WorkLog worklog : worklogs) {
			totalWorkHours += worklog.getHours();
		}

		SalarySlip salarySlip = salarySlipService.get(employeeId, endDateUTC);

		if (salarySlip == null) {
			salarySlip = new SalarySlip();
			salarySlip.setDate(endDateUTC);
			salarySlip.setEmployee(employee);
			salarySlip.setPrintCount(1);
			salarySlipService.post(salarySlip);
		} else {
			salarySlip.setPrintCount(salarySlip.getPrintCount() + 1);
		}

		response.put("Employee Name ", employee.getFirstName() + " " + employee.getLastName());
		response.put("Period ", "From " + startDate + " to " + endDate);
		response.put("Bank Code ", employee.getBankCode());
		response.put("Bank Account No. ", employee.getBankAccNo());
		response.put("Total Worked Hours ", totalWorkHours);
		response.put("Salary Amount ", totalWorkHours * HOURLY_RATE);
		response.put("Print Count ", salarySlip.getPrintCount());

		salarySlipService.post(salarySlip);

		return response;
	}

}
