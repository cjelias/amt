package ca.celias.amt.mvc.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import ca.celias.amt.services.MaintenanceOptionsService;

/**
 * 
 *
 * @author Chris Elias
 */
@RequestScoped
public class EngineType implements MVCModel {

    @Inject
    private MaintenanceOptionsService service;

    @Inject 
    private HttpServletRequest request;

    @Override
    public String view() {
        request.setAttribute("OPTIONS", service.findAllDTO());
        return "/WEB-INF/jsp/enginetype.jsp";
    }
    
    @Override
    public String navigationName() {
        return "NAV_ENGINE_TYPE";
    }
}
