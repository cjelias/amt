package ca.celias.amt.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chris Elias
 */
@XmlRootElement()
public class PatchItem {

    private String op;
    private String path;
    private PatchValue value;
    
    public PatchItem() {
    }

    public PatchItem(String op, String path, PatchValue value) {
        this.op = op;
        this.path = path;
        this.value = value;
    }

    /**
     * @return the op
     */
    public String getOp() {
        return op;
    }

    /**
     * @param op
     *            the op to set
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
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the value
     */
    public PatchValue getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(PatchValue value) {
        this.value = value;
    }
}