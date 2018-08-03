/**
 * 
 */
package ca.celias.amt.services.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * @author Chris Elias
 */
@Entity
@NamedQueries( {
    @NamedQuery(name = EngineType.QUERY_FIND_ALL_DTO, query = "SELECT " + EngineType.CONSTRUCT_PARAM + " FROM EngineType e"),
    @NamedQuery(name = EngineType.QUERY_FIND_DTO, query = "SELECT " + EngineType.CONSTRUCT_PARAM + " FROM EngineType e WHERE e.code =:oid"),
})
@DynamicUpdate
@Table(name="ENGINE_TYPE")
public class EngineType
implements AmtEntity<String> {
    
    public static final String QUERY_FIND_DTO = "EngineType.FindDTO";
    public static final String QUERY_FIND_ALL_DTO = "EngineType.FindAllDTO";
    
    protected static final String CONSTRUCT_PARAM = 
            "new ca.celias.amt.dto.EngineTypeDTO(e.code, e.description)";
    

    @Id
    @Column(name="CODE", length=15, nullable=false, insertable=true, updatable=false)
    private String code;
    
    @Column(name="DESCRIPTION", length=256, nullable=false, insertable=true, updatable=true)
    private String description;
    
    @ManyToMany(cascade = { 
            CascadeType.PERSIST, 
            CascadeType.MERGE
        })
        @JoinTable(name = "ENGINE_MAINTENANCE_OPTION",
            joinColumns = @JoinColumn(name = "ENGINE_CODE"),
            inverseJoinColumns = @JoinColumn(name = "MAINTENANCE_CODE")
        )
    private Set<MaintenanceOption> maintenanceOptions;

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
     * @return the maintenanceOptions
     */
    public Set<MaintenanceOption> getMaintenanceOptions() {
        return maintenanceOptions;
    }

    /**
     * @param maintenanceOptions the maintenanceOptions to set
     */
    public void setMaintenanceOptions(Set<MaintenanceOption> maintenanceOptions) {
        this.maintenanceOptions = maintenanceOptions;
    }
}
