/**
 * 
 */
package ca.celias.amt.mvc;

/**
 * 
 * @author Chris Elias
 */
public class ResultNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3377691852270767404L;
    
    public ResultNotFoundException() {
    }
    
    public ResultNotFoundException(String message) {
        super(message);
    }
}
