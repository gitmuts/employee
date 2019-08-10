package com.company.employee.model;


public class Employee {

	private String  employeeId;
	private String managerId;
	private Integer salary;

	
	
	
	public Employee(String employeeId, String managerId, Integer salary) {
		super();
		this.employeeId = employeeId;
		this.managerId = managerId;
		this.salary = salary;
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getManagerId() {
		return managerId;
	}


	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}


	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}


	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", managerId=" + managerId + ", salary=" + salary + "]";
	}
	
}
