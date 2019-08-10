package com.company.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.employee.service.EmployeeService;

@RestController
public class TestController {

	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> testLogic() {
		
		String csvString ="employee3,'',100,\nemployee2,employee1,200,\nemployee1,employee2,200";
		
		return new ResponseEntity<>(employeeService.parseEmployeesInfo(csvString), HttpStatus.OK);
		
	}
}
