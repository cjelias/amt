/**
 * 
 */
package ca.celias.amt.services;

/**
 * 
 * @author Chris Elias
 */
public class VINExistsException extends RuntimeException {

    private static final long serialVersionUID = -3377691852270767404L;
    
    public VINExistsException(String message) {
        super(message);
    }
}
