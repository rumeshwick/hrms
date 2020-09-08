package com.rusoft.hrms.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.rusoft.hrms.service.DepartmentService;
import com.rusoft.hrms.service.EmployeeService;
import com.rusoft.hrms.model.Department;
import com.rusoft.hrms.model.Employee;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public Map<String, Object> getList() {
		Map<String, Object> response = new HashMap<>();
		response.put("employees", employeeService.getList());
		return response;
	}

	@PostMapping("/save")
	public Map<String, Object> save(@Valid @RequestBody Employee employee, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		String errorMsg = "";
		if (result.hasErrors()) {
			FieldError error = null;
			for (Object obj : result.getAllErrors()) {
				error = (FieldError) obj;
				errorMsg += error.getDefaultMessage() + ". ";
			}
		}

		if (errorMsg.length() != 0) {
			response.put("error", errorMsg);
			return response;
		}

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

		employee = employeeService.post(employee);
		response.put("employee", employee);
		return response;
	}

	@PutMapping("/save/{id}")
	public Map<String, Object> update(@Valid @RequestBody Employee employee, BindingResult result,
			@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		String errorMsg = "";
		if (result.hasErrors()) {
			FieldError error = null;
			for (Object obj : result.getAllErrors()) {
				error = (FieldError) obj;
				errorMsg += error.getDefaultMessage() + ". ";
			}
		}

		if (errorMsg.length() != 0) {
			response.put("error", errorMsg);
			return response;
		}

		if (!employeeService.get(id).isPresent()) {
			response.put("error", "Employee not found");
			return response;
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
		response.put("success", "Employee updated");
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Object> delete(@PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		if (!employeeService.get(id).isPresent()) {
			response.put("error", "Employee not found");
			return response;
		}

		employeeService.delete(employeeService.get(id).get());
		response.put("success", "Employee deleted");
		return response;
	}

	@PostMapping("/image_upload/{id}")
	public Map<String, Object> singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {

		Map<String, Object> response = new HashMap<>();

		if (file.isEmpty()) {
			response.put("error", "Select image");
			return response;
		}

		if (!employeeService.get(id).isPresent()) {
			response.put("error", "Employee not found");
			return response;
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
		response.put("success", "Image uploaded");
		return response;
	}

}
