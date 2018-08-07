/**
 * 
 */
package ca.celias.amt.dto;

import java.util.Objects;

/**
 * 
 * @author Chris Elias
 */
public class MaintenanceOptionDTO {

    private final String code;
    private final String description;

    public MaintenanceOptionDTO() {
        this(null, null);
    }
    
    private MaintenanceOptionDTO(MaintenanceOptionDTOBuilder builder) {
        this(builder.code, builder.description);
    }
    
    public MaintenanceOptionDTO(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        var other = (MaintenanceOptionDTO)obj;        
        return Objects.equals(code, other.code);
    }
    
    /**
     * 
     * @return
     */
    public MaintenanceOptionDTOBuilder builder() {
        return new MaintenanceOptionDTOBuilder(this);
    }
    
    /**
     * 
     * @return
     */
    public static MaintenanceOptionDTOBuilder emptyBuilder() {
        return new MaintenanceOptionDTOBuilder();
    }
    
    
    public static class MaintenanceOptionDTOBuilder {
        private String code;
        private String description;
       
        private MaintenanceOptionDTOBuilder() {
        }
        
        private MaintenanceOptionDTOBuilder(MaintenanceOptionDTO dto) {
            code = dto.code;
            description = dto.description;
        }

        public MaintenanceOptionDTOBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public MaintenanceOptionDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MaintenanceOptionDTO build() {
            return new MaintenanceOptionDTO(this);
        }
    }   
}