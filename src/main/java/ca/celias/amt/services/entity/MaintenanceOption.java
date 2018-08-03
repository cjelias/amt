/**
 * 
 */
package ca.celias.amt.services.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * @author Chris Elias
 */
@Entity(name="MaintenanceOption")
@NamedQueries( {
    @NamedQuery(name = MaintenanceOption.QUERY_FIND_ALL_DTO, query = "SELECT " + MaintenanceOption.CONSTRUCT_PARAM + " FROM MaintenanceOption m"),
    @NamedQuery(name = MaintenanceOption.QUERY_FIND_DTO, query = "SELECT " + MaintenanceOption.CONSTRUCT_PARAM + " FROM MaintenanceOption m WHERE m.code =:oid"),
})
@DynamicUpdate
@Table(name="MAINTENANCE_OPTION")
public class MaintenanceOption
implements AmtEntity<String> {
    
    public static final String QUERY_FIND_DTO = "MaintenanceOption.FindDTO";
    public static final String QUERY_FIND_ALL_DTO = "MaintenanceOption.FindAllDTO";
    
    protected static final String CONSTRUCT_PARAM = 
            "new ca.celias.amt.dto.MaintenanceOptionDTO(m.code, m.description)";

    @Id
    @Column(name="CODE", length=15, nullable=false, insertable=true, updatable=false)
    private String code;
    
    @Column(name="DESCRIPTION", length=256, nullable=false, insertable=true, updatable=true)
    private String description;
    
    @ManyToMany(mappedBy = "maintenanceOptions")
    private Set<EngineType> engineTypes;

    @Override
    public String getOid() {
        return code;
    }

    @Override
    public void setOid(String oid) {
        this.code = oid;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the engineTypes
     */
    public Set<EngineType> getEngineTypes() {
        return engineTypes;
    }

    /**
     * @param engineTypes the engineTypes to set
     */
    public void setEngineTypes(Set<EngineType> engineTypes) {
        this.engineTypes = engineTypes;
    }
}