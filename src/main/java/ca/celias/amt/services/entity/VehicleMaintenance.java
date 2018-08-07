package ca.celias.amt.services.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
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
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * 
 * @author Chris Elias
 */
@Entity
@Table(name="VEHICLE_MAINTENANCE")
@NamedQuery(name=VehicleMaintenance.FIND_BY_VEHICLE,
            query="SELECT v FROM VehicleMaintenance v WHERE v.vehicle = :vehicle ORDER BY v.appointmentDate DESC") 
public class VehicleMaintenance
implements AmtEntity<UUID> {
    
    public static final String FIND_BY_VEHICLE="VehicleMaintenance.FindByVehicle";
    
    @Id
    @Column(name="OID", insertable=true, updatable=false, nullable=false, columnDefinition=UUID_COL_DEF)
    private UUID oid;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity=Vehicle.class)
    @JoinColumn(name = "VEHICLE_OID")
    private Vehicle vehicle;

    @Column(name="APPOINTMENT_DATE")
    private LocalDateTime appointmentDate;
    
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
     * @return the appointmentDate
     */
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @param appointmentDate the appointmentDate to set
     */
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * @return the maintenanceOptions
     */
    public Set<MaintenanceOption> getMaintenanceOptions() {
        if (maintenanceOptions == null)
            maintenanceOptions = new HashSet<>();
        
        return maintenanceOptions;
    }

    /**
     * @param maintenanceOptions the maintenanceOptions to set
     */
    public void setMaintenanceOptions(Set<MaintenanceOption> maintenanceOptions) {
        this.maintenanceOptions = maintenanceOptions;
    }
    
    @PrePersist
    private void prePresist() {
        if (oid == null)
            oid = UUID.randomUUID();
    }    
}
