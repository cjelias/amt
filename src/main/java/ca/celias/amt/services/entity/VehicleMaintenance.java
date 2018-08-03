package ca.celias.amt.services.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Chris Elias
 */
@Entity
@Table(name="VEHICLE_MAINTENANCE")
public class VehicleMaintenance
implements AmtEntity<UUID> {
    
    @Id
    @Column(name="OID", insertable=true, updatable=false, nullable=false, columnDefinition=UUID_COL_DEF)
    private UUID oid;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity=EngineType.class)
    @JoinColumn(name = "VEHICLE_OID")
    private Vehicle vehicle;

    @ManyToMany(cascade = { 
            CascadeType.PERSIST, 
            CascadeType.MERGE
        })
        @JoinTable(name = "VEHICLE_MAINTENANCE_OPTION",
            joinColumns = @JoinColumn(name = "OID"),
            inverseJoinColumns = @JoinColumn(name = "MAINTENANCE_CODE")
        )
    private Set<MaintenanceOption> maintenanceOptions;

    /**
     * @return the oid
     */
    public UUID getOid() {
        return oid;
    }

    /**
     * @param oid the oid to set
     */
    public void setOid(UUID oid) {
        this.oid = oid;
    }

    /**
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
