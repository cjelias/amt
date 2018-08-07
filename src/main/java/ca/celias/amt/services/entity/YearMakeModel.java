/**
 * 
 */
package ca.celias.amt.services.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Chris Elias
 */
@Entity
@Table(name="YEAR_MAKE_MODEL")
public class YearMakeModel
implements AmtEntity<UUID> {

    @Id
    @Column(name="OID", insertable=true, updatable=false, nullable=false, columnDefinition=UUID_COL_DEF)
    private UUID oid;
    
    @Column(name="make_year", length=4, nullable=false)
    private int year;
    
    @Column(name="make", length=4, nullable=false)
    private String make;
    
    @ElementCollection
    private List<String> models;

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
     * @return the models
     */
    public List<String> getModels() {
        return models;
    }

    /**
     * @param models the models to set
     */
    public void setModels(List<String> models) {
        this.models = models;
    }
}