/**
 * 
 */
package ca.celias.amt.services;

/**
 * 
 * @author Chris Elias
 */
public class InvalidEngineTypeFoundException extends RuntimeException {

    private static final long serialVersionUID = -3377691852270767404L;
    
    public InvalidEngineTypeFoundException(String message) {
        super(message);
    }
}
