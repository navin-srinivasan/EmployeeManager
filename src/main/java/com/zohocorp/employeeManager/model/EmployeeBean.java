package com.zohocorp.employeeManager.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployeeBean {

	private Integer employeeId;
	private String employeeName;
	private String employeeMail;
	private String designation;
	private String department;
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeMail() {
		return employeeMail;
	}
	public void setEmployeeMail(String employeeMail) {
		this.employeeMail = employeeMail;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Override
	public String toString() {
		return "employeeBean [employeeId=" + employeeId + ", employeeName=" + employeeName + ", employeeMail="
				+ employeeMail + ", designation=" + designation + ", Department=" + department + "]";
	}

}
