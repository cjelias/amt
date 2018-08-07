/**
 * 
 */
package ca.celias.amt.ws.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * 
 * @author Chris Elias
 */
@Path("/scheduler")
public class SchedulerResource {

    @GET
    public String get() {
        return "some schedule";
    }
}
