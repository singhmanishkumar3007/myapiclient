package com.tech.boot.myclient.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.boot.myclient.entity.Employee;
import com.tech.boot.myclient.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> getAllEmployeeDetails() {

		List<Employee> employeeList = (List<Employee>) employeeRepository.findAll();
		return employeeList;
	}

	@Override
	public Employee getEmployee(String name) {
		// TODO Auto-generated method stub
		return employeeRepository.findByName(name);
	}

	@Override
	public Employee getEmployeeByQuery(String empName) {

		return employeeRepository.findByEmployeeQuery(empName);
	}

	@Override
	public Employee addEmployee(Employee emp) {
		return employeeRepository.save(emp);
	}

	@Override
	public void deleteEmployee(Integer empId) {
		try {
			employeeRepository.deleteById(empId);
		} catch (Exception e) {
			LOGGER.error("exception while deleting emp id {} is ", empId, e);
		}

	}

}
