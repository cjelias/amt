package ca.celias.amt.dto;

import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author Chris Elias
 */
@XmlRootElement(name = "patch")
public class PatchDTO {

    private String op;
    private String path;
    
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String[] value;

    /**
     * @return the op
     */
    public String getOp() {
        return op;
    }

    /**
     * @param op the op to set
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the value
     */
    public String[] getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String[] value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(op, path, value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        var other = (PatchDTO)obj;
        
        return Objects.equals(op, other.op) &&
                Objects.equals(path, other.path) &&
                Objects.equals(value, other.value);
    }
    
}
