package ca.celias.amt.dto;

/**
 *
 * @author Chris Elias
 */
public class PatchValue {

    private String[] nameArr;
    
    public PatchValue() {
    }
    
    public PatchValue(String ...nameArr) {
        this.nameArr = nameArr;
    }

    /**
     * @return the nameArr
     */
    public String[] getNameArr() {
        return nameArr;
    }

    /**
     * @param nameArr the nameArr to set
     */
    public void setNameArr(String[] nameArr) {
        this.nameArr = nameArr;
    }
}
