/**
 * 
 */
package ca.celias.amt.services.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Chris Elias
 */
@Entity
@Table(name="YEAR_MAKE")
public class YearMakeModel {

    @EmbeddedId
    private YearMakeId id;
    
    @ElementCollection
    private List<String> models;

    /**
     * @return the id
     */
    public YearMakeId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(YearMakeId id) {
        this.id = id;
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