package com.tech.boot.myclient.repository;

import org.springframework.data.repository.CrudRepository;

import com.tech.boot.myclient.entity.MyEmployees;

public interface MyEmployeesRepository extends CrudRepository<MyEmployees, Integer> {

	MyEmployees findByUserNameAndPassword(String userName, String password);

}
