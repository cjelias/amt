/**
 * 
 */
package ca.celias.amt.mvc.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * 
 * @author Chris Elias
 */
@Path("/api/scheduler")
public class SchedulerResource {

    @GET
    public String get() {
        return "some schedule";
    }
}
