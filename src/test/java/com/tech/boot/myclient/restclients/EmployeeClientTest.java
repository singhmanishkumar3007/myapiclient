package com.tech.boot.myclient.restclients;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.tech.boot.myclient.config.APIConfigs;
import com.tech.boot.myclient.entity.Employee;
import com.tech.boot.myclient.services.EmployeeService;

//@RunWith(MockitoJUnitRunner.class)
public class EmployeeClientTest {

	@InjectMocks
	private EmployeeClient employeeClient;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HttpHeaders httpHeaders;

	@Mock
	private APIConfigs apiConfigs;

	@Mock
	private EmployeeService employeeServiceMock;

	private String empName;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		empName = "Manish";
		//employeeClient = new EmployeeClient();
	}

	@Test
	public void testGetAllEmployeesFromDB() {
		Employee value = new Employee();
		value.setEmployeeId(1);
		value.setName("manish");
		Mockito.when(employeeServiceMock.getEmployee(Mockito.anyString())).thenReturn(value);
		List<Employee> actualEmployeeList = employeeClient.getAllEmployeesFromDB(empName);
		List<Employee> expectedList = Arrays.asList(value);
		assertEquals(expectedList, actualEmployeeList);

	}

}
