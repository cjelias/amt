package ca.celias.amt.mvc.controller;

import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.celias.amt.resources.HasLogger;

/**
 * 
 * @author Chris Elias
 */
@Path("/controller/scheduler")
public class Scheduler 
implements HasLogger {

    @GET
    @Controller
    public String view() {
        logger().trace("ENTER view()");
        
        try {
            return "/WEB-INF/jsp/scheduler.jsp";
        } finally {
            logger().trace("EXIT view()");
        }
    }

}
