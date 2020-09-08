package com.rusoft.hrms.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.rusoft.hrms.service.DepartmentService;
import com.rusoft.hrms.service.EmployeeService;
import com.rusoft.hrms.model.Department;
import com.rusoft.hrms.model.Employee;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("api/v1/employee")
@RestController
public class EmployeeController {

	@Value("${image.upload.path}")
	private String IMAGE_UPLOADED_FOLDER;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@GetMapping
	public List<Employee> getList() {
		return employeeService.getList();
	}

	@PostMapping("/save")
	public Employee save(@Valid @RequestBody Employee employee) {

		if (employee.getDepartments() != null && employee.getDepartments().size() > 0) {

			List<Integer> depIds = employee.getDepartments().stream().map(department -> department.getId())
					.collect(Collectors.toList());
			;

			List<Department> departments = departmentService.getList().stream()
					.filter(department -> depIds.indexOf(department.getId()) > -1).collect(Collectors.toList());

			employee.setDepartments(departments);

		}

		if (employee.getId() == null) {
			employee.setRegisteredDate(new Date());
		}

		return employeeService.post(employee);
	}

	@PutMapping("/save/{id}")
	public String update(@Valid @RequestBody Employee employee, @PathVariable Integer id) {

		if (!employeeService.get(id).isPresent()) {
			return "Employee not found";
		}

		employee.setId(employeeService.get(id).get().getId());

		if (employee.getDepartments() != null && employee.getDepartments().size() > 0) {

			List<Integer> depIds = employee.getDepartments().stream().map(department -> department.getId())
					.collect(Collectors.toList());
			;

			List<Department> departments = departmentService.getList().stream()
					.filter(department -> depIds.indexOf(department.getId()) > -1).collect(Collectors.toList());

			employee.setDepartments(departments);

		}
		employeeService.post(employee);
		return "Employee updated";
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {

		if (!employeeService.get(id).isPresent()) {
			return "Employee not found";
		}

		employeeService.delete(employeeService.get(id).get());
		return "Employee deleted";
	}

	@PostMapping("/image_upload/{id}")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {

		if (file.isEmpty()) {
			return "Select image";
		}

		if (!employeeService.get(id).isPresent()) {
			return "Employee not found";
		}

		try {

			byte[] bytes = file.getBytes();
			String imageName = id + "_" + file.getOriginalFilename();
			Path path = Paths.get(IMAGE_UPLOADED_FOLDER + imageName);
			Files.write(path, bytes);

			Employee employee = employeeService.get(id).get();
			employee.setImage(imageName);
			employeeService.post(employee);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Image uploaded";
	}

}
