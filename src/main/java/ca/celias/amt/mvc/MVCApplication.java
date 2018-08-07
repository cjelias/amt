/**
 * 
 */
package ca.celias.amt.mvc;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ca.celias.amt.mvc.controller.EngineType;
import ca.celias.amt.mvc.controller.MaintenanceOptions;
import ca.celias.amt.mvc.controller.Vehicles;
import ca.celias.amt.resources.HasLogger;

/**
 * 
 * @author Chris Elias
 */
@ApplicationPath("/ui")
public class MVCApplication extends Application
implements HasLogger {
    @Override
    public Set<Class<?>> getClasses() {
        var classes = new HashSet<Class<?>>();
        
        classes.add(EngineType.class);
        classes.add(MaintenanceOptions.class);
        classes.add(Vehicles.class);
        
        return classes;
    }
}