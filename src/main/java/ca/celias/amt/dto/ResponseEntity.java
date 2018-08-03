package ca.celias.amt.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chris Elias
 */
@XmlRootElement(name="response")
public class ResponseEntity {

    private String status;
    private String errorMessage;
    
    private Map<String,String> errors;
    
    public ResponseEntity() {
    }

    public ResponseEntity(String status) {
        this.status = status;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
