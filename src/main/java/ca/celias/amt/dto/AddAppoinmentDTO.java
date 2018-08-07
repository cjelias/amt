/**
 * 
 */
package ca.celias.amt.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chris Elias
 */
@XmlRootElement(name="addAppoinment")
public class AddAppoinmentDTO {

    private String oid;
    private String appointmentDate;
    private String [] appointmentOption;
    
    /**
     * @return the oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * @param oid the oid to set
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * @return the appointmentDate
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }
    
    /**
     * @param appointmentDate the appointmentDate to set
     */
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    /**
     * @return the appointmentOption
     */
    public String[] getAppointmentOption() {
        return appointmentOption;
    }
    
    /**
     * @param appointmentOption the appointmentOption to set
     */
    public void setAppointmentOption(String[] appointmentOption) {
        this.appointmentOption = appointmentOption;
    }
}
