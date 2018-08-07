package ca.celias.amt.mvc.controller.settings;

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.services.MaintenanceOptionsService;

/**
 * 
 * @author Chris Elias
 */
@Path("/settings/enginetype")
public class EngineType 
implements HasLogger {

    @Inject
    private MaintenanceOptionsService service;
    
    @Inject
    private Models models;
    
    @GET
    @Controller
    public String view() {
        logger().trace("ENTER view()");
        
        try {
            models.put("OPTIONS", service.findAllDTO());
            
            return "/WEB-INF/jsp/settings/enginetype.jsp";
        } finally {
            logger().trace("EXIT view()");
        }
    }
}