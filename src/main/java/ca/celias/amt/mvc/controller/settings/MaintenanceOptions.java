package ca.celias.amt.mvc.controller.settings;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.celias.amt.resources.HasLogger;

/**
 * 
 * @author Chris Elias
 */
@Path("/controller/settings/maintenanceoptions")
@RequestScoped
public class MaintenanceOptions 
implements HasLogger {

    @Inject
    private HttpServletRequest request;
    
    @GET
    @Controller
    public String view() {
        logger().trace("ENTER view()");
        
        try {
            var path = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()) + "/";
            logger().debug("Base Path: {}", path);
            
            return "/WEB-INF/jsp/settings/maintenanceoptions.jsp";
        } finally {
            logger().trace("EXIT view()");
        }
    }
}
