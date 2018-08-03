/**
 * 
 */
package ca.celias.amt.mvc;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ca.celias.amt.resources.HasLogger;

/**
 * 
 * @author Chris Elias
 */
@ApplicationPath("/app")
public class AmtApplication extends Application
implements HasLogger {

}