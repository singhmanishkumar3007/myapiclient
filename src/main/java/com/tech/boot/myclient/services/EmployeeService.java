package com.tech.boot.myclient.services;

import java.util.List;

import com.tech.boot.myclient.entity.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployeeDetails();

	Employee getEmployee(String name);

	Employee getEmployeeByQuery(String empName);

	Employee addEmployee(Employee emp);

	void deleteEmployee(Integer empId);

}
