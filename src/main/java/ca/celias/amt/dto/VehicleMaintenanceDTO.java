package ca.celias.amt.dto;

import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Chris Elias
 */
@XmlRootElement(name="vehicleMaintenance")
public class VehicleMaintenanceDTO {

    private final String oid;
    private final String date;
    private final String options;

    public VehicleMaintenanceDTO() {
        this(null, null, null);
    }

    public VehicleMaintenanceDTO(String oid, String date, String options) {
        this.oid = oid;
        this.date = date;
        this.options = options;
    }
    
    private VehicleMaintenanceDTO(VehicleMaintenanceDTOBuilder builder) {
        this(builder.oid, builder.date, builder.options);
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the options
     */
    public String getOptions() {
        return options;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(date, options);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        var other = (VehicleMaintenanceDTO)obj;
        
        return Objects.equals(date, other.date) &&
                Objects.equals(options, options);
    }
    
    /**
     * 
     * @return
     */
    public VehicleMaintenanceDTOBuilder builder() {
        return new VehicleMaintenanceDTOBuilder(this);
    }
    
    /**
     * 
     * @return
     */
    public static VehicleMaintenanceDTOBuilder emptyBuilder() {
        return new VehicleMaintenanceDTOBuilder();
    }

    
    public static class VehicleMaintenanceDTOBuilder {
        private String oid;
        private String date;
        private String options;
       
        private VehicleMaintenanceDTOBuilder() {
        }
        
        private VehicleMaintenanceDTOBuilder(VehicleMaintenanceDTO dto) {
            oid = dto.oid;
            date = dto.date;
            options = dto.options;
        }
        
        public VehicleMaintenanceDTOBuilder withOid(String oid) {
            this.oid = oid;
            return this;
        }

        public VehicleMaintenanceDTOBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public VehicleMaintenanceDTOBuilder withOptions(String options) {
            this.options = options;
            return this;
        }

        public VehicleMaintenanceDTO build() {
            return new VehicleMaintenanceDTO(this);
        }
    }    
}