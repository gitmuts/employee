package com.company.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.employee.service.EmployeeService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService service;

	@Test
	public void serviceIsNotNull() throws Exception {
		assertThat(service).isNotNull();
	}

	@Test
	public void parsesString() throws Exception {

		String csvString = "employee1,employee2,100,\nemployee2,'',200,";

		String response = service.parseEmployeesInfo(csvString);

		service.employees = new HashMap<>();

		assertThat(response).contains("Csv String parsed successfully");

	}

	@Test
	public void checkWhenNonEmployeeIsPassedAsManager() throws Exception {

		String csvString = "employee1,employee2,100,\nemployee2,employee3,200,";

		String response = service.parseEmployeesInfo(csvString);

		service.employees = new HashMap<>();
		assertThat(response).contains("Non employee found in managers list");

	}

	@Test
	public void checkWhenSalaryIsNotInteger() throws Exception {

		String csvString = "employee1,employee2,100c,\nemployee2,'',200,";

		String response = service.parseEmployeesInfo(csvString);

		service.employees = new HashMap<>();
		assertThat(response).contains("Salary: 100c not valid integer");

	}

	@Test
	public void employeeReportsToMoreThanOneManager() throws Exception {

		String csvString = "employee1,employee2,100,\nemployee1,'',200,";

		String response = service.parseEmployeesInfo(csvString);

		service.employees = new HashMap<>();
		assertThat(response).contains("List contain duplicates of employee with id employee1");

	}

	@Test
	public void testSalaryBudgetofManager() throws Exception {

		String csvString = "employee1,'',100,\nemployee2,employee1,200,";

		String response = service.parseEmployeesInfo(csvString);

		long salary = service.getManagerSalaryBudget("''");
		service.employees = new HashMap<>();
		assertThat(salary).isEqualTo(salary);

	}

}
