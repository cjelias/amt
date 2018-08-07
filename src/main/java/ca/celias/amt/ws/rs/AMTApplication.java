/**
 * 
 */
package ca.celias.amt.ws.rs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ca.celias.amt.resources.HasLogger;

/**
 * 
 * @author Chris Elias
 */
@ApplicationPath("/api")
public class AMTApplication extends Application
implements HasLogger {
    @Override
    public Set<Class<?>> getClasses() {
        var classes = new HashSet<Class<?>>();
        
        classes.add(EngineTypeResources.class);
        classes.add(MaintenanceOptionsResource.class);
        classes.add(VehicleResource.class);
        
        return classes;
    }
}