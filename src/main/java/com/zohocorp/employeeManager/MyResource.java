package com.zohocorp.employeeManager;

import com.zohocorp.employeeManager.service.EmployeeManagerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    private EmployeeManagerService service;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt()  {
        try {
            System.out.println("In resource");
            this.service = new EmployeeManagerService();
            this.service.populate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "Got it!";
    }
}
