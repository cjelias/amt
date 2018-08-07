/**
 * 
 */
package ca.celias.amt.mvc.model;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import ca.celias.amt.services.EngineTypeService;

/**
 *
 * @author Chris Elias
 */
@RequestScoped
public class Vehicle implements MVCModel {
    
    @Inject
    private EngineTypeService service;

    @Inject 
    private HttpServletRequest request;

    @Override
    public String view() {
        var vehicleId = request.getParameter("vehicleId");
        var engineType = request.getParameter("engineType");
        
        if (Objects.isNull(vehicleId) && Objects.isNull(engineType)) {
            request.setAttribute("ENGINE_TYPES", service.findAllDTO());
            return "/WEB-INF/jsp/vehicles.jsp";
        } else {
            request.setAttribute("OPTIONS", service.getOptions(engineType));
            request.setAttribute("VEHICLE_OID",vehicleId);

            return "/WEB-INF/jsp/addAppointment.jsp";    
        }
        
    }
    
    @Override
    public String navigationName() {
        return "NAV_VEHICLE";
    }
}