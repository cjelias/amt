/**
 * 
 */
package ca.celias.amt.services.entity;

/**
 * 
 * @author Chris Elias
 */
public interface AmtEntity<T> {
    
    // Column Def for DERBY
    public String UUID_COL_DEF = "CHAR(16) FOR BIT DATA";
    
    // for MySQL
//    private static final String UUID_COL_DEF = "binary(16)";

  // for ORACLE
//    private static final String UUID_COL_DEF = "RAW(16)";

  // for ORACLE
//    private static final String UUID_COL_DEF = "UNIQUEIDENTIFIER";
    
    
    T getOid();
    
    void setOid(T oid);

}
