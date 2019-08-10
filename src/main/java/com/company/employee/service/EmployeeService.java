package com.company.employee.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.employee.model.Employee;
import com.opencsv.CSVReader;

@Service
public class EmployeeService {

	private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	public static Map<String, List<Employee>> employees = new HashMap<>();
	
	public String parseEmployeesInfo(String csvString) {
		try {
		
		
		
		List<String> employeesParsed = new ArrayList<>();
		
		
		CSVReader reader = new CSVReader(new StringReader(csvString));
		
		 String[] nextRecord;
			while ((nextRecord = reader.readNext()) != null) {
			    
			     String employeeId = nextRecord[0];
			     String managerId = nextRecord[1];
			     String salaryString = nextRecord[2];
			     //validate salary
			     boolean salaryValid = validateSalary(salaryString);
			     if(!salaryValid) {
			    	 return String.format("Salary: %s not valid integer", salaryString);
			     }
			     
			     //after process employee add them to list of processed employees
			     if(employeesParsed.contains(employeeId)) {
			    	 return "List contain duplicates of employee with id "+ employeeId;
			     }
			     
			     Integer salary = Integer.parseInt(salaryString);
			     
			     Employee employee = new Employee(employeeId, managerId, salary);
			     
			     List<Employee> employeeList = new ArrayList<>();
			     
			     List<Employee> employeesAssignedToManager = employees.get(managerId);
			     
			     if(employeesAssignedToManager == null) {
			    	 employeesAssignedToManager = new ArrayList<>();
			     }
			     
			     //before adding check if the employee reports to another manager
			     boolean alreadyHasManager= checkIfEmployeeReportsToAnotherManager(employee, employees);
			     
			     if(alreadyHasManager) {
			    	 return String.format("Employee %s already has a manager", employeeId);
			     }
			     
			     employeesAssignedToManager.add(employee);
			     employeesParsed.add(employeeId);
			     employees.put(managerId, employeesAssignedToManager);
			 }
			
			logger.info(employees.toString());
			//check if there is a manager that is not an employee
			boolean nonEmployee = checkManagerNonEmployee(employeesParsed, employees);
			
			if(nonEmployee) {
				return "Non employee found in managers list";
			}
			
			return "Csv String parsed successfully";
		}catch(Exception e) {
			logger.error(e.getMessage());
			return "Error occurred while parsing info " + e.getMessage();
		}
	}
	
	private boolean checkManagerNonEmployee(List<String> employeeList, Map<String, List<Employee>> employeesMap) {
		try {
			
			logger.info(employeeList.toString());
			Set<String> managers = employeesMap.keySet();
			
			logger.info(managers.toString());
			for(String manager: managers) {
				boolean contains = employeeList.contains(manager);
				if(!contains && !manager.equals("''")) {
					return true;
				}
			}
			
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	private boolean validateSalary(String salary) {
		try {
			Integer.parseInt(salary);
			return true;
		}catch(Exception e) {
			logger.error("Salary: {} not valid integer ", salary);
			return false;
		}
	}
	
	private boolean checkIfEmployeeReportsToAnotherManager(Employee e, Map<String, List<Employee>> employees) {
		try {
			
			for(String key: employees.keySet()) {
				List<Employee> employeeList = employees.get(key);
				
				logger.info(employeeList.toString() + " "+ e.getEmployeeId() + " " + key);
				for(Employee employee: employeeList) {
					if(employee.getEmployeeId().equals(e.getEmployeeId()))
						return true;
				}
			}
			
			return false;
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}
	}
	
	public long getManagerSalaryBudget(String manager) {
		try {
			
			List<Employee> managerEmployeesList = employees.get(manager);
			
			List<Employee> allEmployeesUnderManager = new ArrayList<>();
			allEmployeesUnderManager.addAll(managerEmployeesList);
			
			for(Employee e: managerEmployeesList) {
				List<Employee> employeeUnderThis = employees.get(e.getEmployeeId());
				
				if(employeeUnderThis != null) {
					allEmployeesUnderManager.addAll(employeeUnderThis);
				}
			}
			
			long salaryBudget =0;
			
			for(Employee e: allEmployeesUnderManager) {
				salaryBudget += e.getSalary();
			}
			
			
			
			return salaryBudget;
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			return 0l;
		}
	}
}
