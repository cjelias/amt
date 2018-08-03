/**
 *
 */
package ca.celias.amt.dto;

import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chris Elias
 */
@XmlRootElement(name = "vehicle")
public class VehicleDTO {

    private final String oid;
    private final int year;
    private final String make;
    private final String model;
    private final double odometerReading;
    private final String engineType;
    
    public VehicleDTO() {
        this(null, 0, null, null, 0, null);
    }
    
    public VehicleDTO(String oid, int year, String make, String model, 
            double odometerReadering, String engineType) {
        this.oid = oid;
        this.year = year;
        this.make = make;
        this.model = model;
        this.odometerReading = odometerReadering;
        this.engineType = engineType;
    }

    /**
     * @return the oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the make
     */
    public String getMake() {
        return make;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @return the odometerReading
     */
    public double getOdometerReading() {
        return odometerReading;
    }

    /**
     * @return the engineType
     */
    public String getEngineType() {
        return engineType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(oid);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        var other = (VehicleDTO)obj;
        
        return Objects.equals(oid, other.oid);
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}