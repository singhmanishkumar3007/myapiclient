package com.tech.boot.myclient.restclients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tech.boot.myclient.annotations.MyAnnotation;
import com.tech.boot.myclient.config.APIConfigs;
import com.tech.boot.myclient.constants.LogConstants;
import com.tech.boot.myclient.domain.EmployeeDomain;
import com.tech.boot.myclient.entity.Employee;
import com.tech.boot.myclient.services.EmployeeService;

@RestController
public class EmployeeClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeaders httpHeaders;

	// @Value("${api.geturl.url}")
	// String urlForGet;
	@Autowired
	private APIConfigs apiConfigs;

	@Autowired
	private EmployeeService employeeService;

	@Value("${api.posturl.url}")
	String urlForPost;

	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeClient.class);

	@MyAnnotation
	@GetMapping(value = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeDomain> fetchAllEmployees() {

		LOGGER.info("in employee controller ");
		LOGGER.info("client ip is {}", MDC.get(LogConstants.API_CLIENT_IP));
		HttpEntity<?> requestEntity = new HttpEntity<Object>("", headerBuilder());

		ResponseEntity<List<EmployeeDomain>> responseEntity = restTemplate.exchange(apiConfigs.getUrl(), HttpMethod.GET,
				requestEntity, new ParameterizedTypeReference<List<EmployeeDomain>>() {
				}, "");
		// LOGGER.info("in emplotee controller, response
		// is"+responseEntity.getBody().toString());
		LOGGER.info("in employee controller, response is {} ,{}", responseEntity.getBody().toString(),
				requestEntity.toString());
		return responseEntity.getBody();

	}

	// ideally it should bve post. to call from browser, we habe made it GET . This
	// client method should be called from some other method or client and it will
	// internally call the service layer for posting.

	@GetMapping(value = "/addEmployees", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeDomain> addEmployees() throws Exception {

		EmployeeDomain e1 = new EmployeeDomain();
		e1.setEmployeeName("test");
		ResponseEntity<List<EmployeeDomain>> responseEntity = null;

		try {
			HttpEntity<EmployeeDomain> requestEntity = new HttpEntity<EmployeeDomain>(e1, headerBuilder());
			responseEntity = restTemplate.exchange(urlForPost, HttpMethod.POST, requestEntity,
					new ParameterizedTypeReference<List<EmployeeDomain>>() {
					}, "");
		} catch (Exception e) {
			throw e;
		}

		return responseEntity.getBody();

	}

	@GetMapping(value = "/fetchEmployeeByPathVariable", produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeDomain fetchAllEmployeesByPathVariable() {
		HttpEntity<?> requestEntity = new HttpEntity<Object>("", headerBuilder());

		Integer departmentId = 1;
		ResponseEntity<EmployeeDomain> responseEntity = restTemplate.exchange(
				"http://localhost:6060/myapi/getEmployee/{departmentId}", HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<EmployeeDomain>() {
				}, departmentId);

		return responseEntity.getBody();

	}

	@GetMapping(value = "/fetchEmployeeByPathVariableAndRequestParams", produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeDomain fetchAllEmployeesByPathVariableAndRequestparams() {
		HttpEntity<?> requestEntity = new HttpEntity<Object>("", headerBuilder());

		Integer departmentId = 1;
		String empName = "Rajesh";
		ResponseEntity<EmployeeDomain> responseEntity = restTemplate.exchange(
				"http://localhost:6060/myapi/getEmployeeByFilter/{departmentId}?name={empName}", HttpMethod.GET,
				requestEntity, new ParameterizedTypeReference<EmployeeDomain>() {
				}, departmentId, empName);

		return responseEntity.getBody();

	}

	// api to talk to service layer and database

	@GetMapping(value = "getAllEmployees", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployeesFromDB(@RequestParam(value = "emp_name", required = false) String empName) {

		List<Employee> employees = new ArrayList<>();
		if (null == empName) {
			employees = employeeService.getAllEmployeeDetails();
		} else {
			Employee emp = employeeService.getEmployee(empName);
			employees = Arrays.asList(emp);
		}
		return employees;
	}

	// api to talk to service layer and database with custom query
	@GetMapping(value = "getEmployeeByName", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getEmployeeFromDB(@RequestParam(value = "emp_name", required = true) String empName) {

		Employee employee = null;

		employee = employeeService.getEmployeeByQuery(empName);

		return employee;
	}

	@PostMapping(value = "addEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee addEmployeeToDB(@RequestBody Employee emp) {

		Employee employee = null;

		employee = employeeService.addEmployee(emp);

		return employee;
	}

	@PutMapping(value = "updateEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee upsertEmployeeToDB(@RequestBody Employee emp) {

		Employee employee = null;

		employee = employeeService.addEmployee(emp);

		return employee;
	}

	@DeleteMapping(value = "deleteEmployee/{emp_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteEmployeeFromDB(@PathVariable("emp_id") Integer employeeId) {

		employeeService.deleteEmployee(employeeId);

	}

	private HttpHeaders headerBuilder() {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}

}
