/**
 * 
 */
package ca.celias.amt.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Chris Elias
 */
public interface HasLogger {

    /**
     * 
     * @return
     */
    default Logger logger() {
        return LoggerFactory.getLogger(getClass());
    }
}
