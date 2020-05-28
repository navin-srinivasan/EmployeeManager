package com.zohocorp.employeeManager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.zohocorp.employeeManager.DAO.Schedule;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.zohocorp.employeeManager.service.EmployeeManagerService;
import com.zohocorp.employeeManager.model.EmployeeBean;
import com.zohocorp.employeeManager.model.UserBean;


@Path("/resource")
public class EmployeeManagerController {

    private EmployeeManagerService service;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String userString) {
        System.out.println("In LOGIN");
        UserBean userBean;
        try {
            this.service = new EmployeeManagerService();
            JSONObject jsonObject = this.jsonToString(userString);
            userBean = this.service.login(jsonObject.get("username").toString(), jsonObject.get("password").toString());
            return Response.status(Status.OK).entity(userBean).build();
        } catch (Exception exception) {
            System.out.println(exception);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(String username) {
        System.out.println("In LOGOUT");
        try {
            this.service = new EmployeeManagerService();
            this.service.logout(username);
            return Response.status(Status.OK).build();
        } catch (Exception exception) {
            System.out.println(exception);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/schedule")
    @Produces(MediaType.APPLICATION_JSON)
    public String schedule() {
        System.out.println("In schedule Methode");
        try {
            Schedule schedule = new Schedule();
            return "Schedule initiated";
        }catch(Exception e){
            System.out.println(e);
            return "Schedule failed";
        }
    }

    @GET
    @Path("/poplate")
    @Produces(MediaType.APPLICATION_JSON)
    public String populate() {
        try {
            System.out.println("In resource");
            this.service = new EmployeeManagerService();
            this.service.populate();
            return "Success";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "Fail";
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecord() {
        System.out.println("In GET Metho");
        List<EmployeeBean> employeeList = new ArrayList<EmployeeBean>();
        try {
            this.service = new EmployeeManagerService();
            employeeList = this.service.getAllEmployees();
            GenericEntity<List<EmployeeBean>> genericEntity = new GenericEntity<List<EmployeeBean>>(employeeList) {
            };
            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception exception) {
            System.out.println(exception);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/deleteRecord/{addedby}/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteRecord(@PathParam("addedby") String addedby, @PathParam("id") int employeeId) {
        String msg = "";
        try {
            this.service = new EmployeeManagerService();
            this.service.deleteEmployee(addedby, employeeId);
            return Response
                    .status(Response.Status.OK)
                    .entity(msg)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (Exception exception) {
            System.out.println(exception);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/fetch/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecord(@PathParam("id") int employeeId) {
        EmployeeBean employeeBean = new EmployeeBean();
        try {
            this.service = new EmployeeManagerService();
            employeeBean = this.service.getEmployee(employeeId);
            return Response.status(Status.OK).entity(employeeBean).build();
        } catch (Exception exception) {
            System.out.println(exception);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/createRecord")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createRecord(String employeeBeanString) {
        System.out.println(employeeBeanString);
        String msg = "";
        try {
            JSONObject jsonObject = this.jsonToString(employeeBeanString);
            EmployeeBean employeeBean = this.jsonToBean(jsonObject);
            this.service = new EmployeeManagerService();
            this.service.createEmployee(employeeBean, (String) jsonObject.get("addedby"));
            return Response
                    .status(Response.Status.OK)
                    .entity(msg)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (Exception exception) {
            System.out.println(exception);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/updateRecord")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateRecord(String employeeBeanString) {
        System.out.println(employeeBeanString);
        String msg = "";
        try {
            JSONObject jsonObject = this.jsonToString(employeeBeanString);
            EmployeeBean employeeBean = this.jsonToBean(jsonObject);
            this.service = new EmployeeManagerService();
            this.service.updateEmployee(employeeBean, (String) jsonObject.get("addedby"));
            return Response
                    .status(Response.Status.OK)
                    .entity(msg)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (Exception exception) {
            System.out.println(exception);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    public JSONObject jsonToString(String value) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(value);
    }

    public EmployeeBean jsonToBean(JSONObject jsonObject) throws ParseException {
        EmployeeBean employeeBean = new EmployeeBean();
        employeeBean.setEmployeeId(Integer.parseInt((String) jsonObject.get("employeeId")));
        employeeBean.setEmployeeName(jsonObject.get("employeeName").toString());
        employeeBean.setEmployeeMail(jsonObject.get("employeeMail").toString());
        employeeBean.setDepartment(jsonObject.get("department").toString());
        employeeBean.setDesignation(jsonObject.get("designation").toString());
        return employeeBean;
    }

}
