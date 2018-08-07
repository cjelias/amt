package ca.celias.amt.mvc.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import ca.celias.amt.services.EngineTypeService;

/**
 *
 * @author Chris Elias
 */
@Path("vehicles")
@RequestScoped
public class Vehicles {

    @Inject
    private EngineTypeService service;
    
    @Inject
    private Models models;

    @GET
    @Controller
    public String view() {
        models.put("NAV_VEHICLE", "active" );
        models.put("ENGINE_TYPES", service.findAllDTO());
        return "/WEB-INF/jsp/vehicles.jsp";
    }
    
    /**
     * 
     * @param vehicleId
     * @param engineType
     * @return
     */
    @Path("{vehicleId}/addappointment/{engineType}")
    @GET
    @Controller
    public String viewAddAppointment(@PathParam("vehicleId") String vehicleId, @PathParam("engineType") String engineType) {
        models.put("OPTIONS", service.getOptions(engineType));
        models.put("VEHICLE_OID", vehicleId);

        return "/WEB-INF/jsp/addAppointment.jsp";
    }
}