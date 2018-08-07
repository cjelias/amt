package ca.celias.amt.mvc.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.celias.amt.resources.HasLogger;

/**
 * 
 * @author Chris Elias
 */
@Path("maintenanceoptions")
@RequestScoped
public class MaintenanceOptions 
implements HasLogger {

    @Inject
    private Models models;

    @GET
    @Controller
    public String view() {
        models.put("NAV_MAINTENANCE_OPTIONS", "active" );
        return "/WEB-INF/jsp/maintenanceoptions.jsp";
    }
}
