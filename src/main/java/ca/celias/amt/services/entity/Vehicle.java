/**
 * 
 */
package ca.celias.amt.services.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * @author Chris Elias
 */
@Entity
@NamedQueries( {
    @NamedQuery(name = Vehicle.QUERY_FIND_ALL_DTO, query = "SELECT " + Vehicle.CONSTRUCT_PARAM + " FROM Vehicle v"),
    @NamedQuery(name = Vehicle.QUERY_FIND_DTO, query = "SELECT " + Vehicle.CONSTRUCT_PARAM + " FROM Vehicle v WHERE v.oid =:oid"),
    @NamedQuery(name = Vehicle.QUERY_CHECK_VIN, query = "SELECT v FROM Vehicle v WHERE v.vin = :vin"),
})
@DynamicUpdate
@Table(name="VEHICLE")
public class Vehicle 
implements AmtEntity<UUID> {

    public static final String QUERY_FIND_DTO = "Vehicle.FindDTO";
    public static final String QUERY_FIND_ALL_DTO = "Vehicle.FindAllDTO";
    public static final String QUERY_CHECK_VIN = "Vehicle.CheckVIN";
    
    protected static final String CONSTRUCT_PARAM = 
            "new ca.celias.amt.dto.VehicleDTO(v.oid, v.vin, v.year, v.make, v.model, v.odometerReading, v.engineType.code)";

    @Id
    @Column(name="OID", insertable=true, updatable=false, nullable=false, columnDefinition=UUID_COL_DEF)
    private UUID oid;
    
    @Column(name="VIN", length=17)
    private String vin;

    @Column(name="VEHICLE_YEAR", length=4)
    private int year;
    
    @Column(name="MAKE", length=15)
    private String make;
    
    @Column(name="MODEL", length=15)
    private String model;

    @Column(name="ODOMETER_READING", length=8, precision=1, nullable=true)
    private Double odometerReading;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity=EngineType.class)
    @JoinColumn(name = "ENGINE_TYPE")
    private EngineType engineType;

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
     * @return the vin
     */
    public String getVin() {
        return vin;
    }

    /**
     * @param vin the vin to set
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the make
     */
    public String getMake() {
        return make;
    }

    /**
     * @param make the make to set
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the odometerReading
     */
    public Double getOdometerReading() {
        return odometerReading;
    }

    /**
     * @param odometerReading the odometerReading to set
     */
    public void setOdometerReading(Double odometerReading) {
        this.odometerReading = odometerReading;
    }

    /**
     * @return the engineType
     */
    public EngineType getEngineType() {
        return engineType;
    }

    /**
     * @param engineType the engineType to set
     */
    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }
    
    @PrePersist
    private void prePresist() {
        if (oid == null)
            oid = UUID.randomUUID();
    }
}