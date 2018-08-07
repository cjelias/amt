/**
 *
 */
package ca.celias.amt.dto;

import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chris Elias
 */
@XmlRootElement(name = "vehicle")
public class VehicleDTO {

    private String oid;
    private String vin;
    private int year;
    private String make;
    private String model;
    private Double odometerReading;
    private String engineType;
    
    public VehicleDTO() {
        this(null, null, 0, null, null, null, null);
    }

    private VehicleDTO(VehicleDTOBuilder builder) {
        this(builder.oid, builder.vin, builder.year,
             builder.make, builder.model, builder.odometerReading,
             builder.engineType);
    }

    public VehicleDTO(UUID oid, String vin, int year, String make, String model, 
            Double odometerReadering, String engineType) {
        this.oid = oid == null ? null : oid.toString();
        this.vin = vin;
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
     * @return the vin
     */
    public String getVin() {
        return vin;
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
    public Double getOdometerReading() {
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
    
    /**
     * 
     * @return
     */
    public VehicleDTOBuilder builder() {
        return new VehicleDTOBuilder(this);
    }
    
    /**
     * 
     * @return
     */
    public static VehicleDTOBuilder emptyBuilder() {
        return new VehicleDTOBuilder();
    }
    
    public static class VehicleDTOBuilder {
        private UUID oid;
        private String vin;
        private int year;
        private String make;
        private String model;
        private Double odometerReading;
        private String engineType;
       
        private VehicleDTOBuilder() {
        }
        
        private VehicleDTOBuilder(VehicleDTO dto) {
            oid = dto.oid == null ? null : UUID.fromString(dto.oid);
            vin = dto.vin;
            year = dto.year;
            make = dto.make;
            model = dto.model;
            odometerReading = dto.odometerReading;
            engineType = dto.engineType;
        }

        public VehicleDTOBuilder withOid(UUID oid) {
            this.oid = oid;
            return this;
        }

        public VehicleDTOBuilder withOid(String oid) {
            this.oid = UUID.fromString(oid);
            return this;
        }

        public VehicleDTOBuilder withVin(String vin) {
            this.vin = vin;
            return this;
        }

        public VehicleDTOBuilder withYear(int year) {
            this.year = year;
            return this;
        }

        public VehicleDTOBuilder withMake(String make) {
            this.make = make;
            return this;
        }

        public VehicleDTOBuilder withModel(String model) {
            this.model = model;
            return this;
        }
        
        public VehicleDTOBuilder withOdometerReading(Double odometerReading) {
            this.odometerReading = odometerReading;
            return this;
        }

        public VehicleDTOBuilder withEngineType(String engineType) {
            this.engineType = engineType;
            return this;
        }
        
        public VehicleDTO build() {
            return new VehicleDTO(this);
        }
    }
}