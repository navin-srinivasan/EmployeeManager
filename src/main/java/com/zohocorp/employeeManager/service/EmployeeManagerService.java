package com.zohocorp.employeeManager.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.adventnet.ds.query.DataSet;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.zohocorp.employeeManager.DAO.EmployeeManagerDAO;
import com.zohocorp.employeeManager.model.EmployeeBean;
import com.zohocorp.employeeManager.model.UserBean;

public class EmployeeManagerService {

    EmployeeManagerDAO employeeManagerDAO;
    UserBean userBean;

    public EmployeeManagerService() throws Exception {
        this.employeeManagerDAO = new EmployeeManagerDAO();
    }

    public void populate() throws Exception {
        System.out.println("In service");
        this.employeeManagerDAO.populate();
    }

    public UserBean login(String username, String password) throws Exception {
        DataObject dataObject = this.employeeManagerDAO.login(username);
        Iterator<Row> iter = dataObject.getRows("Login");
        if (iter.hasNext()) {
            Row row = iter.next();
            if (row.get("Login_PASSWORD").equals(password)) {
                this.userBean = new UserBean();
                this.userBean.setUsername((String) row.get("Login_USERNAME"));
                this.userBean.setLoginCode((String) row.get("Login_Code"));
                this.employeeManagerDAO.addActiveUser(username);
            }
        }
        return this.userBean;
    }

    public List<EmployeeBean> getAllEmployees() throws Exception {
        List<EmployeeBean> employeeList = new ArrayList<EmployeeBean>();
        DataObject dataObject = this.employeeManagerDAO.getAllEmployees();
        Iterator<Row> iter = dataObject.getRows("Employee");
        while (iter.hasNext()) {
            Row row = iter.next();
            employeeList.add(this.beanConversion(row));
        }
        return employeeList;
    }

    public EmployeeBean getEmployee(int employeeId) throws Exception {
        DataObject dataObject = this.employeeManagerDAO.getEmployee(employeeId);
        Iterator<Row> iter = dataObject.getRows("Employee");
        EmployeeBean employeeBean = null;
        if (iter.hasNext()) {
            employeeBean = this.beanConversion(iter.next());
        }
        return employeeBean;
    }

    public void createEmployee(EmployeeBean employeeBean, String addedby) throws Exception {
        this.checkUser(addedby);
        this.employeeManagerDAO.createEmployee(employeeBean);
    }

    public void updateEmployee(EmployeeBean employeeBean, String addedby) throws Exception {
        this.checkUser(addedby);
        this.employeeManagerDAO.updateEmployee(employeeBean);
    }

    public void deleteEmployee(String addedby, int employeeId) throws Exception {
        this.checkUser(addedby);
        this.employeeManagerDAO.deleteEmployee(employeeId);
    }

    public void logout(String username) throws Exception {
        this.employeeManagerDAO.logout(username);
    }

    public void checkUser(String addedby) throws Exception {
        DataSet dataSet = this.employeeManagerDAO.checkUser(addedby);
        System.out.println(dataSet);
        if (dataSet.next()) {
            if (!dataSet.getValue("Login_Code").equals("ML")) {
                throw new Exception("Invalid User");
            }
        } else {
            throw new Exception("Invalid User");
        }

    }

    public EmployeeBean beanConversion(Row row) throws Exception {
        EmployeeBean employeeBean = new EmployeeBean();
        employeeBean.setEmployeeId(Math.toIntExact((Long) row.get("Employee_ID")));
        employeeBean.setEmployeeName((String) row.get("Employee_NAME"));
        employeeBean.setEmployeeMail((String) row.get("Employee_MAIL"));
        employeeBean.setDesignation((String) row.get("Employee_DESIGNATION"));
        employeeBean.setDepartment((String) row.get("Employee_DEPARTMENT"));
        return employeeBean;
    }

}
