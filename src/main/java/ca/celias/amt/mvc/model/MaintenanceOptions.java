/**
 * 
 */
package ca.celias.amt.mvc.model;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Chris Elias
 */
@RequestScoped
public class MaintenanceOptions implements MVCModel {

    /* (non-Javadoc)
     * @see ca.celias.amt.mvc.model.MVCModel#view()
     */
    @Override
    public String view() {
        return "/WEB-INF/jsp/maintenanceoptions.jsp";
    }
    
    @Override
    public String navigationName() {
        return "NAV_MAINTENANCE_OPTIONS";
    }
}
