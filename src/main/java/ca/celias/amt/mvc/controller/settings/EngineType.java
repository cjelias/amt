package ca.celias.amt.mvc.controller.settings;

import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.celias.amt.resources.HasLogger;

/**
 * 
 * @author Chris Elias
 */
@Path("/controller/settings/enginetype")
public class EngineType 
implements HasLogger {

    @GET
    @Controller
    public String view() {
        logger().trace("ENTER view()");
        
        try {
            return "/WEB-INF/jsp/settings/enginetype.jsp";
        } finally {
            logger().trace("EXIT view()");
        }
    }
}