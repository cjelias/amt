/**
 * 
 */
package ca.celias.amt.services.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Chris Elias
 */
@Embeddable
public class YearMakeId implements Serializable {

    private static final long serialVersionUID = -3883181297095155359L;

    @Column(name="make_year", length=4, nullable=false)
    private int year;
    
    @Column(name="make", length=4, nullable=false)
    private String make;

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

    @Override
    public int hashCode() {
        return Objects.hash(year, make);
    }
    
    @Override
    public boolean equals(Object obj) {
        return Objects.equals(year, year) &&
                Objects.equals(make, make);
    }
    
}
