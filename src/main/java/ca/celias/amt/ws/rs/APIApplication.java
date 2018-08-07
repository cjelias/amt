/**
 * 
 */
package ca.celias.amt.ws.rs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ca.celias.amt.resources.HasLogger;
import ca.celias.amt.ws.rs.settings.EngineTypeResources;
import ca.celias.amt.ws.rs.settings.MaintenanceOptionsResource;

/**
 * 
 * @author Chris Elias
 */
@ApplicationPath("/api")
public class APIApplication extends Application
implements HasLogger {
    
    @Override
    public Set<Class<?>> getClasses() {
        var classes = new HashSet<Class<?>>();
        
        classes.add(EngineTypeResources.class);
        classes.add(MaintenanceOptionsResource.class);
        classes.add(SchedulerResource.class);
        classes.add(VehicleResource.class);
        return classes;
    }
}