package com.tech.boot.myclient.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tech.boot.myclient.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>

{
	Employee findByName(String employeeName);

	Employee findByNameAndEmployeeId(String empName, Integer id);
	
	void deleteByEmployeeIdAndName(Integer id,String name);
	
	@Query("SELECT employee  FROM Employee employee WHERE employee.name=:empName")
	Employee findByEmployeeQuery(@Param("empName")String employeeName);

}
