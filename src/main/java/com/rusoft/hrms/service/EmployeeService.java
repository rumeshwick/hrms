package com.rusoft.hrms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rusoft.hrms.model.Employee;
import com.rusoft.hrms.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getList() {
        return employeeRepository.findAll();
    }

    public Employee post(Employee entity) {
        Employee e = employeeRepository.save(entity);
        return e;
    }

    public Optional<Employee> get(Integer id) {
        Optional<Employee> e = employeeRepository.findById(id);
        return e;
    }

    public void delete(Employee entity) {
        employeeRepository.delete(entity);
    }

}
