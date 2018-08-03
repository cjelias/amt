/**
 * 
 */
package ca.celias.amt.services;

/**
 * 
 * @author Chris Elias
 */
public class ResultNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3377691852270767404L;
    
    public ResultNotFoundException(String message) {
        super(message);
    }
}
