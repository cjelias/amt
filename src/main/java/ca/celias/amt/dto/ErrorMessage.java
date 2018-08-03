package ca.celias.amt.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Chris Elias
 */
@XmlRootElement
public class ErrorMessage {

    /** 
     * contains the same HTTP Status code returned by the server 
     */
    @XmlElement(name = "status")
    private int status;
    
    /** 
     * message describing the error
     */
    @XmlElement(name = "message")
    private String message;
 
    public ErrorMessage() {
    }
    
    public ErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}