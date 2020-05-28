package com.zohocorp.employeeManager.DAO;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.*;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.*;
import com.zohocorp.employeeManager.model.EmployeeBean;
import org.eclipse.persistence.sessions.Login;

import java.sql.Connection;


public class EmployeeManagerDAO {


    private DataObject dataObject;
    Persistence persistence;

    public EmployeeManagerDAO() throws Exception {
        this.persistence = (Persistence) BeanUtil.lookup("Persistence");

    }

    public void populate() throws Exception{
        System.out.println("In DAO");
        this.dataObject = new WritableDataObject();
        Row r = new Row("Login");
        r.set("Login_USERNAME","berlin@abc.com");
        r.set("Login_PASSWORD", "password");
        r.set("Login_Code", "ML");
        this.dataObject.addRow(r);

        r = new Row("Login");
        r.set("Login_USERNAME","nairobi@abc.com");
        r.set("Login_PASSWORD", "password");
        r.set("Login_Code", "EL");
        this.dataObject.addRow(r);

        r = new Row("Employee");
        r.set("Employee_ID", 1);
        r.set("Employee_NAME","Berlin");
        r.set("Employee_MAIL", "berlin@abc.com");
        r.set("Employee_DESIGNATION", "Manager");
        r.set("Employee_DEPARTMENT", "Cliq");
        this.dataObject.addRow(r);
        this.persistence.add(this.dataObject);

    }

    public DataObject login(String username) throws Exception {
        SelectQuery selectQuery = new SelectQueryImpl(Table.getTable("Login"));
        selectQuery.addSelectColumn(Column.getColumn(null, "*"));
        selectQuery.setCriteria(new Criteria(Column.getColumn("Login", "Login_USERNAME"), username, QueryConstants.EQUAL));
        return this.persistence.get(selectQuery);
    }

    public DataObject getAllEmployees() throws Exception {
        SelectQuery selectQuery = new SelectQueryImpl(Table.getTable("Employee"));
        selectQuery.addSelectColumn(Column.getColumn(null, "*"));
        selectQuery.addSortColumn(new SortColumn(Column.getColumn("Employee", "Employee_ID"), true));
        return this.persistence.get(selectQuery);
    }

    public DataObject getEmployee(int employeeId) throws Exception {
        SelectQuery selectQuery = new SelectQueryImpl(Table.getTable("Employee"));
        selectQuery.addSelectColumn(Column.getColumn(null, "*"));
        selectQuery.setCriteria(new Criteria(Column.getColumn("Employee", "Employee_ID"), employeeId, QueryConstants.EQUAL));
        return this.persistence.get(selectQuery);
    }

    public void createEmployee(EmployeeBean employeeBean) throws Exception {
        this.dataObject = new WritableDataObject();
        Row r = new Row("Employee");
        r.set("Employee_ID", employeeBean.getEmployeeId());
        r.set("Employee_NAME", employeeBean.getEmployeeName());
        r.set("Employee_MAIL", employeeBean.getEmployeeMail());
        r.set("Employee_DESIGNATION", employeeBean.getDesignation());
        r.set("Employee_DEPARTMENT", employeeBean.getDepartment());
        this.dataObject.addRow(r);
        this.persistence.add(this.dataObject);
    }

    public void updateEmployee(EmployeeBean employeeBean) throws Exception {
        UpdateQuery updateQuery = new UpdateQueryImpl("Employee");
        updateQuery.setUpdateColumn("Employee_NAME", employeeBean.getEmployeeName());
        updateQuery.setUpdateColumn("Employee_MAIL", employeeBean.getEmployeeMail());
        updateQuery.setUpdateColumn("Employee_DESIGNATION", employeeBean.getDesignation());
        updateQuery.setUpdateColumn("Employee_DEPARTMENT", employeeBean.getDepartment());
        updateQuery.setCriteria(new Criteria(Column.getColumn("Employee", "Employee_ID"), employeeBean.getEmployeeId(), QueryConstants.EQUAL));
        this.persistence.update(updateQuery);
    }

    public void deleteEmployee(int employeeId) throws Exception {
        Row row = new Row("Employee");
        row.set("Employee_ID", employeeId);
        this.persistence.delete(row);
    }

    public void addActiveUser(String username) throws Exception {
        this.dataObject = new WritableDataObject();
        Row row = new Row("ActiveUser");
        row.set("ActiveUser_USERNAME", username);
        this.dataObject.addRow(row);
        this.dataObject = this.persistence.add(this.dataObject);
        System.out.println(this.dataObject);
    }

    public void logout(String username) throws Exception {
        Row row = new Row("ActiveUser");
        row.set("ActiveUser_USERNAME", username);
        this.persistence.delete(row);
    }

    public DataSet checkUser(String addedby) throws Exception {
        SelectQuery selectQuery = new SelectQueryImpl(new Table("Login"));
        selectQuery.addSelectColumn(Column.getColumn("Login", "Login_Code"));
        selectQuery.addJoin(new Join("Login", "ActiveUser", new String[]{ "Login_USERNAME"}, new String[]{ "ActiveUser_USERNAME"}, Join.INNER_JOIN));
        selectQuery.setCriteria(new Criteria(Column.getColumn("Login", "Login_USERNAME"), addedby, QueryConstants.EQUAL));
        Connection conn = RelationalAPI.getInstance().getConnection();
        DataSet ds = RelationalAPI.getInstance().executeQuery(selectQuery, conn);
        return ds;
    }

}
