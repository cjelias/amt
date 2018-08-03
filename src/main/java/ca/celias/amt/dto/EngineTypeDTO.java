package ca.celias.amt.dto;

import java.util.Objects;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.istack.NotNull;

/**
 * 
 * @author Chris Elias
 */
@XmlRootElement(name = "engineType")
public final class EngineTypeDTO {

    @NotNull
    @Size(max=15)
    private final String code;
    
    @NotNull
    @Size(max=256)
    private final String description;

    public EngineTypeDTO() {
        this(null, null);
    }
    
    private EngineTypeDTO(EngineTypeDTOBuilder builder) {
        this(builder.code, builder.description);
    }
    
    public EngineTypeDTO(String code, String description) {
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

        var other = (EngineTypeDTO)obj;
        
        return Objects.equals(code, other.code);
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    /**
     * 
     * @return
     */
    public EngineTypeDTOBuilder builder() {
        return new EngineTypeDTOBuilder(this);
    }
    
    /**
     * 
     * @return
     */
    public static EngineTypeDTOBuilder emptyBuilder() {
        return new EngineTypeDTOBuilder();
    }
    
    
    public static class EngineTypeDTOBuilder {
        private String code;
        private String description;
       
        private EngineTypeDTOBuilder() {
        }
        
        private EngineTypeDTOBuilder(EngineTypeDTO dto) {
            code = dto.code;
            description = dto.description;
        }

        public EngineTypeDTOBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public EngineTypeDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EngineTypeDTO build() {
            return new EngineTypeDTO(this);
        }
    }
}