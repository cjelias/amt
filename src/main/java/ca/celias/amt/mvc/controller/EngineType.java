package ca.celias.amt.mvc.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.celias.amt.services.MaintenanceOptionsService;

/**
 * 
 * @author Chris Elias
 */
@Path("enginetype")
@RequestScoped
public class EngineType {

    @Inject
    private MaintenanceOptionsService service;

    @Inject
    private Models models;

    @GET
    @Controller
    public String view() {
        models.put("NAV_ENGINE_TYPE", "active" );
        models.put("OPTIONS", service.findAllDTO());
        
        return "/WEB-INF/jsp/enginetype.jsp";
    }
    
}