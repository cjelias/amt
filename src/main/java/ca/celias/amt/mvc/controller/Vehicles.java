package ca.celias.amt.mvc.controller;

import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.services.EngineTypeService;

/**
 * 
 * @author Chris Elias
 */
@Path("/controller/vehicles")
public class Vehicles 
implements HasLogger {

    @Inject
    private EngineTypeService service;
    
    @Inject
    private Models models;

    @GET
    @Controller
    public String view() {
        logger().trace("ENTER view()");
        
        try {
            models.put("ENGINE_TYPES", service.findAllDTO());
            
            return "/WEB-INF/jsp/vehicles.jsp";
        } finally {
            logger().trace("EXIT view()");
        }
    }

}
